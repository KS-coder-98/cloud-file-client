package cloud.file.management.server.model.communication;

import cloud.file.management.common.FileMessage;
import cloud.file.management.common.Message;

import java.io.*;
import java.util.List;

public class Receive extends Thread{
    private ObjectInputStream in;
    List<Message> msgList; //todo check this now use useless

    public Receive(ObjectInputStream in, List<Message> msgList){
        this.in = in;
        this.msgList = msgList;
    }

    public void run(){
        Message inputObject;
        try {
            while ((inputObject = (Message)in.readObject())!=null){
                inputObject.preprocess();
//                if ( inputObject instanceof FileMessage )
//                    msgList.add(inputObject);
            }
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
}
