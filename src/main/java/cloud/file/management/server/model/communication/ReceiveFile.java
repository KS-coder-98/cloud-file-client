package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;
import cloud.file.management.server.model.Convert;
import cloud.file.management.server.model.FileAPI;
import cloud.file.management.server.model.LambdaExpression;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ReceiveFile extends Thread {
    private InputStream in;
    List<Message> msgList;

    public ReceiveFile(InputStream in, List<Message> msgList) {
        this.in = in;
        this.msgList = msgList;
    }

    public void run() {
        while (true) {
            if (!msgList.isEmpty()) {
                try {
                    System.err.println("zaczołem ");
                    FileChannel fileChannel = null;

                    byte[] id = new byte[Long.BYTES];
                    System.out.println("czyta id");
                    in.read(id);
                    System.out.println("odczytane id= " + Convert.bytesToLong(id));
                    Message msg = findMsgMetaData(Convert.bytesToLong(id));

                    //create file
                    //todo tu zmienione z msg->login for msg->getPathDst
                    var path = FileAPI.createFoldersFromPath(Path.of(msg.getPath()), msg.getPathDst());
                    System.out.println("sciezka + " + path);
                    fileChannel = FileChannel.open(Path.of(path),
                            EnumSet.of(StandardOpenOption.CREATE,
                                    StandardOpenOption.TRUNCATE_EXISTING,
                                    StandardOpenOption.WRITE)
                    );

                    //read file
                    byte[] sizeFile = new byte[Long.BYTES];
                    in.read(sizeFile);
                    long sizeL = Convert.bytesToLong(sizeFile);
                    System.out.println("rozmiar pliku: " + sizeL);

                    //set size for buffer
                    long bufferSize = 1024;
                    if (bufferSize > sizeL) {
                        bufferSize = sizeL;
                    }
                    byte[] bufferFile = new byte[Math.toIntExact(bufferSize)];
                    readAndSaveFile(fileChannel, Math.toIntExact(bufferSize), bufferFile, sizeL);
                    System.out.println("save complit");
                    if (!msg.getLogin().equals(msg.getPathDst())) {
                        System.out.println("dobra zrobi się");
                        //todo wysylanie do usera
                        //todo check it
                        if (!msg.getLogin().equals(msg.getPathDst())) {
                            msg.setId(new Random().nextLong());
                            msg.setLogin(msg.getPathDst());
                            LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                                    t -> t.getSend().getMsgList().add(msg),
                                    t -> t.getLogin().equals(msg.getPathDst())
                            );
                        }
                    }
                    System.err.println("skonczyłem!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Message findMsgMetaData(long id) {
        while (true) {

            var message = LambdaExpression.find(msgList, msg -> id == msg.getId());
            System.out.println("id szukane " + id);
            System.out.println("wiadomosci: " + msgList.toString());
            if (!Objects.isNull(message)) {
                msgList.remove(message);
                System.out.println("znalazlo metadane");
                return message;
            }
            //wait if msg no receive yet
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readAndSaveFile(FileChannel fileChannel, int sizeBufferForFile, byte[] buffer, long sizeL) throws IOException {
        long countReadBytes;
        while (true) {
            if (!((countReadBytes = in.read(buffer)) > 0))
                break;
            fileChannel.write(ByteBuffer.wrap(buffer));
            sizeL -= countReadBytes;
            System.out.println("rozmiar pozaostały : " + sizeL);
            if (sizeL < sizeBufferForFile)
                buffer = new byte[Math.toIntExact(sizeL)];
        }
        fileChannel.close();
    }
}
