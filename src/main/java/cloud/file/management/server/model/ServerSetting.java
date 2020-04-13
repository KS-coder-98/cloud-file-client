package cloud.file.management.server.model;

import java.nio.file.Path;

public class ServerSetting {
    private static Path pathToUserResources;
    private static int port;
    private static boolean sendListOfUser; //todo

    public static Path getPathToUserResources() {
        return pathToUserResources;
    }

    public static void setPathToUserResources(Path pathToUserResources) {
        ServerSetting.pathToUserResources = pathToUserResources;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ServerSetting.port = port;
    }

    public ServerSetting() {
        pathToUserResources = Path.of("D:\\AAAfolderToProject");
        port = 5555;
    }
}
