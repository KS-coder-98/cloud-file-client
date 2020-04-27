package cloud.file.management.server.model.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * The EchoMultiServer class implements main features as handle for users
 *
 *
 * @author Krzysztof Sękowski
 * @version 1.0
 * @since 26.04.2020
 */
public class EchoMultiServer {
    private static ServerSocket socket;
    private static ServerSocket socketForFile;
    private static int port;
    private static List<EchoClientHandle> listUser;

    /**
     * Creates EchoMultiServer, bound to the specified port.
     *
     * @param port This is the number of port which communication process start on
     */
    public EchoMultiServer(int port) {
        EchoMultiServer.port = port;
        listUser = new ArrayList<>();
    }

    /**
     * This function is static. 'Run' function is the main thread in EchoMultiServer class.
     * It is in charge of creating sockets and adding new a client to server.
     *
     * The first socket is assigned to variable 'socket'. This socket runs on port number taken value from class
     * variable 'port'. This socket is responsible for sending metadata between server and client.
     *
     * The second socket is assigned to variable 'socketForFile'. This socket runs on port number taken value from class and
     * increases by one. This socket is responsible for exchanging files between the server and the client
     *
     * 'listUser' stores all clients in the server. EchoClientHandle adds to the list every time
     * when exchange data between server and client starts
     */
    public static void run() {
        try {
            socket = new ServerSocket(port);
            socketForFile = new ServerSocket(port+1);
            while (true){
                EchoClientHandle echoClientHandle = new EchoClientHandle(socket.accept(), socketForFile.accept());
                echoClientHandle.start();
                listUser.add(echoClientHandle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    /**
     * Stop function is in charge of closing socket
     */
    public static void stop() {
        try {
            socket.close();
            socketForFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function returns list user's handle
     *
     * @return This returns the list of handle user's list
     */
    public static List<EchoClientHandle> getListUser() {
        return listUser;
    }
}
