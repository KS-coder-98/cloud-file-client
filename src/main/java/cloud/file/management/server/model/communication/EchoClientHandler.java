package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;

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

    public EchoClientHandler(Socket socket) {
        try {
            List<Message> msgList = Collections.synchronizedList(new ArrayList<>());
            receive = new Receive(new ObjectInputStream(socket.getInputStream()), msgList);
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
}
