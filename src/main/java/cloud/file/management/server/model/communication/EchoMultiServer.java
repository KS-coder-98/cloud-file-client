package cloud.file.management.server.model.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class EchoMultiServer {
    private static ServerSocket socket;
    private static ServerSocket socketForFile;
    private static int port;
    private static List<EchoClientHandler> listUser;

    public EchoMultiServer(int port) {
        EchoMultiServer.port = port;
        listUser = new ArrayList<>();
    }

    public static void run() {
        try {
            socket = new ServerSocket(port);
            socketForFile = new ServerSocket(port+1);
            while (true){
                EchoClientHandler echoClientHandler = new EchoClientHandler(socket.accept(), socketForFile.accept());
                echoClientHandler.start();
                listUser.add(echoClientHandler);
            }
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

    public static List<EchoClientHandler> getListUser() {
        return listUser;
    }
}
