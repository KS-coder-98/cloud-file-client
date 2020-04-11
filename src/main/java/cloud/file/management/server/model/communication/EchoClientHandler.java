package cloud.file.management.server.model.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class EchoClientHandler extends Thread{
    private Receive receive;

    public EchoClientHandler(Socket socket){
        try {
            receive = new Receive(new ObjectInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        receive.run();
    }
}
