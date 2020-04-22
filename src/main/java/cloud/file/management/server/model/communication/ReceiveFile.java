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

public class ReceiveFile extends Thread{
    private InputStream in;
    List<Message> msgList;

    public ReceiveFile(InputStream in, List<Message>msgList){
        this.in = in;
        this.msgList = msgList;
    }

    public void run() {
        while (true) {
            if (!msgList.isEmpty()) {
                try {
                    System.err.println("odbiera plik1!!!");
                    FileChannel fileChannel = null;

                    byte[] id = new byte[Long.BYTES];
                    in.read(id);
                    System.err.println("odebrane id z buffera "+ Convert.bytesToLong(id));

                    System.err.println("odbiera plik2!!!");

                    Message msg = findMsgMetaData(Convert.bytesToLong(id));
                    System.out.println(msg.getLogin() + " " + msg.getPath());
                    System.err.println("odbiera plik3!!!");

                    //create file
                    var path = FileAPI.createFoldersFromPath(Path.of(msg.getPath()), msg.getPathDst());
                    System.out.println("sciezka + " + path);
                    fileChannel = FileChannel.open(Path.of(path),
                            EnumSet.of(StandardOpenOption.CREATE,
                                    StandardOpenOption.TRUNCATE_EXISTING,
                                    StandardOpenOption.WRITE)
                    );

                    System.out.println("czyta plik");
                    //read file
                    byte[] sizeFile = new byte[Long.BYTES];
                    in.read(sizeFile);
                    long sizeL = Convert.bytesToLong(sizeFile);

                    //set size for buffer
                    int bufferSize = 1024;
                    if (bufferSize > sizeL) {
                        bufferSize = (int) sizeL;
                    }
                    byte[] bufferFile = new byte[bufferSize];
                    readAndSaveFile(fileChannel, bufferSize, bufferFile, sizeL);
                    System.out.println("save complit");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("lista wiadmosc jest pusta");
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
            System.out.println("rozmiar listy: "+ msgList.size());
            for ( Message msg : msgList ){
                if ( id == msg.getId() ){
                    System.err.println("równe");
                }
                else{
                    System.out.println("rózne "+ id + " "+msg.getId());
                }
            }
            var message = LambdaExpression.find(msgList, msg -> id == msg.getId());
            if (!Objects.isNull(message)){
                msgList.remove(message);
                return message;
            }
            //wait if msg no receive yet
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readAndSaveFile(FileChannel fileChannel, int sizeBufferForFile, byte[] buffer, long sizeL) throws IOException {
        int countReadBytes;
        while (true) {
            if (!((countReadBytes = in.read(buffer)) > 0))
                break;
            fileChannel.write(ByteBuffer.wrap(buffer));
            sizeL -= countReadBytes;
            if (sizeL < sizeBufferForFile)
                buffer = new byte[(int)sizeL];
        }
        fileChannel.close();
    }
}
