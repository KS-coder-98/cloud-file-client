package cloud.file.management.server.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class implements features work with file
 */
public abstract class FileAPI {

    /**
     * This function process path to saving file
     *
     * @param path path where file was read
     * @param userDst login user, which going to saving file
     * @return Return complete path to save file
     */
    public static String createFoldersFromPath(Path path, String userDst) {
        String[] splitPath = path.toString().split("\\\\");
        String partOfPart = ServerSetting.getPathToUserResources().toString() + "\\" + userDst;
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
            return ServerSetting.getPathToUserResources() + "\\" + userDst + "\\" + path;
        }
    }
}
