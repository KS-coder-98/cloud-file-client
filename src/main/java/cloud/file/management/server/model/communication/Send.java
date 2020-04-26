package cloud.file.management.server.model.communication;

import cloud.file.management.common.FileMessage;
import cloud.file.management.common.Message;
import cloud.file.management.server.model.LambdaExpression;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Class implements sending metadata message. This class extends interface Thread. Communication based on ObjectOutStream
 */
public class Send extends Thread {
    private ObjectOutputStream out;
    List<Message> msgList;

    /**
     * Create object with capabilities to sending object
     *
     * @param out object represents stream to sending serialisation object
     * @param msgList list is implemented as queue with message to send.
     *
     */
    public Send(ObjectOutputStream out, List<Message> msgList) {
        this.out = out;
        this.msgList = msgList;
    }

    /**
     * Function send message to the right client
     *
     * @param msg message with metadata which is sending
     */
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

    /**
     * The main thread in function send. This function reads message from list and sends to client.
     * After sending message the message is being deleted from the list
     */
    public void run() {
        while (true) {
            if ( !msgList.isEmpty() ){
                send(msgList.get(0));
                msgList.remove(0);
            }else
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * @return Return ObjectOutputStream is assigned to object this class
     */
    public ObjectOutputStream getOut() {
        return out;
    }

    /**
     * @param out  Set ObjectOutputStream to object this class
     */
    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * @return Return List of Message assigned to object this class
     */
    public List<Message> getMsgList() {
        return msgList;
    }

    /**
     * @param msgList Set List to object this class
     */
    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }
}
