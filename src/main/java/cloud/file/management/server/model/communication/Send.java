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
            send(new ListUserMessage());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
