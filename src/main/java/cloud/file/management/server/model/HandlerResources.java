package cloud.file.management.server.model;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class HandlerResources {

    /**
     * List directory is implements recursive.
     *
     * @param path localisation which from generate list of all directory
     * @return Return treeItem with list directory from path. type TreeItem is need to javaFx
     * @throws FileNotFoundException throw exception when not file exists or another error with this path
     */
    public static TreeItem<String> listDirectory(Path path) throws FileNotFoundException {
        if (!Files.exists(path))
            throw new FileNotFoundException("wrong path");
        String dirPath = path.toString();
        File dir = new File(dirPath);
        File[] firstLevelFiles = dir.listFiles();
        TreeItem<String> root = new TreeItem<>(dirPath);
        if (firstLevelFiles != null && firstLevelFiles.length > 0) {
            for (File aFile : firstLevelFiles) {
                if (aFile.isDirectory()) {
                    root.getChildren().add(listDirectory(Path.of(aFile.getPath())));
                } else {
                    root.getChildren().add(new TreeItem<>(aFile.getName()));
                }
            }
        }
        return root;
    }

    public static FileTime date(Path path) {
        List<FileTime> fileTimes = new ArrayList<>();
        try (Stream<Path> pathStream = Files.walk(path)) {
            pathStream.forEach(s -> {
                try {
                    fileTimes.add(Files.getLastModifiedTime(s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.max(fileTimes);
    }

    public static List<String> listNameFile(String login){
        List<String> fileList = null;
        Path path = Path.of( ServerSetting.getPathToUserResources().toString()+"\\"+login);
        int index = (path.toString()).length();
        try (Stream<Path> pathStream = Files.walk(path)){
            fileList = pathStream.filter(p->Files.isRegularFile(p))
                    .map(Path::toString)
                    .map(s->s.substring(index))
                    .filter(s -> s.length()>0)
                    .map(s->s.replaceFirst("\\\\", ""))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }
}
