package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;
import cloud.file.management.server.model.LambdaExpression;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class ReceiveFile extends Thread{
    private InputStream in;
    List<Message> msgList;

    public ReceiveFile(InputStream in, List<Message>msgList){
        this.in = in;
        this.msgList = msgList;
    }

    public void run(){
        while (true){
            if(!msgList.isEmpty()){
                try{
                    byte[] id = new byte[Long.BYTES];
                    in.read(id);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Message findMsgMetaData(long id) {
        while (true) {
            var message = LambdaExpression.find(listFileToSave, msg -> id == msg.getId());
            if (!Objects.isNull(message))
                return message;
            //wait if msg no receive yet
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
