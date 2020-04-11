package cloud.file.management.server.app;

import cloud.file.management.server.model.communication.EchoMultiServer;

public class AppMain {
    public static void main(String[] args) {
        EchoMultiServer server = new EchoMultiServer();
        server.start(5555);
    }

}
