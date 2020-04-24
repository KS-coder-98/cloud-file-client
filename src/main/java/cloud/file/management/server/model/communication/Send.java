package cloud.file.management.server.model.communication;

import cloud.file.management.common.FileMessage;
import cloud.file.management.common.Message;
import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.ServerSetting;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

public class Send extends Thread {
    private ObjectOutputStream out;
    List<Message> msgList;

    public Send(ObjectOutputStream out, List<Message> msgList) {
        this.out = out;
        this.msgList = msgList;
    }

    public void send(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            if ( msg instanceof FileMessage){
                LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                        handler -> handler.getSendFile().sendFile(Path.of( msg.getPath()), msg.getId(), msg.getLogin()),
                        handler->handler.getLogin().equals(msg.getLogin())
                        );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            if ( !msgList.isEmpty() ){
                send(msgList.get(0));
                msgList.remove(0);
                System.out.println("wysłano wiadomosc");
            }else
                System.out.println("nie ma nic do wyslania");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }
}
