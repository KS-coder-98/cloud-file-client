package cloud.file.management.server.model.serverTasks;

import cloud.file.management.server.model.ServerSetting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class implements loading information about user
 */
public abstract class LoadUserResources {

    /**
     * Function returns list of paths user which registered on server
     *
     * @return return list of users's path or null when something goes wrong
     */
    public static List<String> loadUsers() {
        try (Stream<Path> walk = Files.walk(ServerSetting.getPathToUserResources(), 1)) {
            return walk
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Function return list of user login
     *
     * @return Returns users login
     */
    public static List<String> loadListUser() {
        var list = loadUsers();
        var index = ServerSetting.getPathToUserResources().toString().length();
        List<String> userNameList = new ArrayList<>();
        for (String path : list) {
            path = path.substring(index);
            if (path.length() != 0)
                userNameList.add(path.replaceFirst("\\\\", ""));
        }
        return userNameList;
    }
}
