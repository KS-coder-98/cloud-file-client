package cloud.file.management.server.model.communication;

import java.io.IOException;
import java.net.ServerSocket;

public class EchoMultiServer {
    private static ServerSocket socket;
    private static int port;

    public EchoMultiServer(int port) {
        EchoMultiServer.port = port;
    }

    public static void run() {
        try {
            socket = new ServerSocket(port);
            while (true)
                new EchoClientHandler(socket.accept()).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public static void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
