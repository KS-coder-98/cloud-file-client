package cloud.file.management.server.app;

import cloud.file.management.server.model.FileAPI;
import cloud.file.management.server.model.ServerSetting;


import java.nio.file.Path;

public class ConsolMain {
    public static void main(String[] args) {
        ServerSetting serverSetting = new ServerSetting();
        //todo test
        var path = "ksiązki\\tablice helion\\test";
        var path2 = "XXX";
        var result = FileAPI.createFoldersFromPath(Path.of(path), path2);
        System.out.println(result);
    }

}
