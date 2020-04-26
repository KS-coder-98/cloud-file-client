package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements handle for a client and it is extended by Thread interface. This class stores data about client
 *
 */
public class EchoClientHandler extends Thread {
    private Receive receive;
    private Send send;
    private String login;
    private List<Message> msgList;
    private List<Message> msgListReceive;
    private ReceiveFile receiveFile;
    private SendFile sendFile;

    /**
     * Created the handle client and it is bound with client with specified socket for metadata and specified socket for the transfer
     * files
     *
     * @param socket socket is responsible for exchanging metadata between the client and the server
     * @param socketForFile socket is responsible for exchanging files between client and server
     */
    public EchoClientHandler(Socket socket, Socket socketForFile) {
        try {
            msgList = Collections.synchronizedList(new ArrayList<>());
            msgListReceive = Collections.synchronizedList(new ArrayList<>());
            receive = new Receive(new ObjectInputStream(socket.getInputStream()), msgListReceive);
            receiveFile = new ReceiveFile(socketForFile.getInputStream(), msgListReceive);
            try {
                var msg = (Message)receive.getIn().readObject();
                login = msg.getLogin();
                msg.preprocess();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            var outPutFile = socketForFile.getOutputStream();
            send = new Send(new ObjectOutputStream(socket.getOutputStream()), msgList);
            sendFile = new SendFile(outPutFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function turns on main thread responsible for sending and receiving data in relation between server-client
     */
    @Override
    public void run() {
        receive.start();
        send.start();
        receiveFile.start();
    }

    /**
     * Returns the name of client
     *
     * @return name of client
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets user the nick
     *
     * @param login user's nick
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns object Receive which is responsible for receiving metadata
     *
     * @return Return Receive object
     */
    public Receive getReceive() {
        return receive;
    }

    /**
     * Sets receive object for the client
     *
     * @param receive is responsible for receiving metadata
     */
    public void setReceive(Receive receive) {
        this.receive = receive;
    }

    /**
     * Returns the object responsible for sending metadata between server-client
     *
     * @return Returns object send
     */
    public Send getSend() {
        return send;
    }

    /**
     * Sets object responsible for sending metadata between server-client
     *
     * @param send object responsible for sending metadata
     */
    public void setSend(Send send) {
        this.send = send;
    }

    /**
     * @return returns user's list with metadata message
     */
    public List<Message> getMsgList() {
        return msgList;
    }

    /**
     * @param msgList list of metadata message
     */
    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }

    /**
     * @return Returns ReceiveFile
     */
    public ReceiveFile getReceiveFile() {
        return receiveFile;
    }

    /**
     * @param receiveFile Object responsible for receiving files
     */
    public void setReceiveFile(ReceiveFile receiveFile) {
        this.receiveFile = receiveFile;
    }

    /**
     * @return Returns the receiving list of message from a client
     */
    public List<Message> getMsgListReceive() {
        return msgListReceive;
    }

    /**
     * @param msgListReceive Lists with message received metadata
     */
    public void setMsgListReceive(List<Message> msgListReceive) {
        this.msgListReceive = msgListReceive;
    }

    /**
     * @return Returns object responsible for sending metadata to client
     */
    public SendFile getSendFile() {
        return sendFile;
    }

    /**
     * @param sendFile Object responsible for sending metadata file to client
     */
    public void setSendFile(SendFile sendFile) {
        this.sendFile = sendFile;
    }
}
