package cloud.file.management.server.model.communication;

import java.io.IOException;
import java.net.ServerSocket;

public class EchoMultiServer {
    private ServerSocket socket;

    public void start(int port){
        try {
            socket = new ServerSocket(port);
            while (true)
                new EchoClientHandler(socket.accept()).start();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            stop();
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
