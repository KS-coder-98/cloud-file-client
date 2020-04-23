package cloud.file.management.server.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileAPI {

    public static String createFoldersFromPath(Path path, String dst) {
        //dst -> login user (name user's folder)
        String[] splitPath = path.toString().split("\\\\");
        String partOfPart = ServerSetting.getPathToUserResources().toString() + "\\" + dst;
        if (splitPath.length > 1) {
            for (int i = 0; i < splitPath.length - 1; i++) {
                partOfPart = partOfPart + "\\" + splitPath[i];
                if (!Files.exists(Path.of(partOfPart))) {
                    try {
                        Files.createDirectories(Path.of(partOfPart));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return partOfPart + "\\" + splitPath[splitPath.length - 1];
        } else {
            return ServerSetting.getPathToUserResources() + "\\" + dst + "\\" + path;
        }
    }
}
