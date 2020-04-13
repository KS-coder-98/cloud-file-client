package cloud.file.management.server.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileAPI {

    public static String createFoldersFromPath(Path path, String dst) {
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

    public static byte[] getStreamFile(Path path) {
        try (FileInputStream fileInputStream = new FileInputStream(String.valueOf(path))) {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in file fileApi !!!!!!!!!!!!!!!!!!!");
            return null;
        }
    }

    public static void saveFile(String login, String path, String dstUser, byte[] file) {
        var pathDst = createFoldersFromPath(Path.of(path), dstUser);
        try (FileOutputStream fileOutputStream = new FileOutputStream(pathDst)) {
            fileOutputStream.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
