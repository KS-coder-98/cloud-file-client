package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;

import java.io.*;
import java.util.List;

/**
 * This class adds possibility to receive Object in type ObjectInputStream and can keep this message in list if this is necessary
 */
public class Receive extends Thread{
    private ObjectInputStream in;
    List<Message> msgList; //todo check this now use useless

    /**
     * Created object is responsible for receiving ObjectInputStream, and binds specified ObjectInputStream, and binds list for message
     *
     * @param in It's object stream input  for specific client. This parameter is responsible for receiving metadata message from client
     * @param msgList List which stores necessary metadata message from client
     */
    public Receive(ObjectInputStream in, List<Message> msgList){
        this.in = in;
        this.msgList = msgList;
    }

    /**
     * The main thread class Receive. It must start to correct work with receiving metadata message
     */
    public void run(){
        Message inputObject;
        try {
            while ((inputObject = (Message)in.readObject())!=null){
                inputObject.preprocess();
            }
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return Return list which consists of metadata message
     */
    public List<Message> getMsgList() {
        return msgList;
    }

    /**
     * @param msgList list which consists of metadata message
     */
    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }

    /**
     * @return ObjectInputStream - this object is responsible for receiving metadata message
     */
    public ObjectInputStream getIn() {
        return in;
    }

    /**
     * @param in set object in type ObjectInputStream.It is responsible for receiving metadata message
     */
    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
}
