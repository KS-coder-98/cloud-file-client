package cloud.file.management.server.model.communication;

import cloud.file.management.common.ListUserMessage;
import cloud.file.management.common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
//            send(new ListUserMessage());
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
