package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;
import cloud.file.management.server.model.event.ServerTask;

import java.io.IOException;
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

    private ReceiveFile receiveFile;

    public EchoClientHandler(Socket socket, Socket socketForFile) {
        try {
            List<Message> msgList = Collections.synchronizedList(new ArrayList<>());
            receive = new Receive(new ObjectInputStream(socket.getInputStream()), msgList);
//            receiveFile = new ReceiveFile()
            try {
                var msg = (Message)receive.getIn().readObject();
                login = msg.getLogin();
                msg.preprocess();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            send = new Send(new ObjectOutputStream(socket.getOutputStream()), msgList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        receive.start();
        send.start();
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
}
