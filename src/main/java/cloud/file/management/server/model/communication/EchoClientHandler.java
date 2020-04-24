package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;
import cloud.file.management.server.model.event.ServerTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EchoClientHandler extends Thread {
    private Receive receive;
    private Send send;
    private String login;
    private List<Message> msgList;
    private List<Message> msgListReceive;
    private ReceiveFile receiveFile;
    private SendFile sendFile;

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

    @Override
    public void run() {
        receive.start();
        send.start();
        receiveFile.start();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Receive getReceive() {
        return receive;
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }

    public Send getSend() {
        return send;
    }

    public void setSend(Send send) {
        this.send = send;
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }

    public ReceiveFile getReceiveFile() {
        return receiveFile;
    }

    public void setReceiveFile(ReceiveFile receiveFile) {
        this.receiveFile = receiveFile;
    }

    public List<Message> getMsgListReceive() {
        return msgListReceive;
    }

    public void setMsgListReceive(List<Message> msgListReceive) {
        this.msgListReceive = msgListReceive;
    }

    public SendFile getSendFile() {
        return sendFile;
    }

    public void setSendFile(SendFile sendFile) {
        this.sendFile = sendFile;
    }
}
