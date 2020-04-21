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
            //todo
        }
    }
}
