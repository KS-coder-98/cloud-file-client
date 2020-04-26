package cloud.file.management.common;

import cloud.file.management.server.model.ServerSetting;
import cloud.file.management.server.model.event.ServerTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class extend Message class. Target this class information about new user
 */
public class JoinMessage extends Message {
    public JoinMessage(String login) {
        super(login);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess JoinMessage");
        Path path = Path.of(ServerSetting.getPathToUserResources() + "\\" + this.getLogin());
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
