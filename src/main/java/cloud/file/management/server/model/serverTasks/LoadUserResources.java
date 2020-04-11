package cloud.file.management.server.model.serverTasks;

import cloud.file.management.server.model.ServerSetting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class LoadUserResources {

    public static List<String> loadUsers(){
        try (Stream<Path> walk = Files.walk(ServerSetting.getPathToUserResources(), 1)) {
            return walk
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
