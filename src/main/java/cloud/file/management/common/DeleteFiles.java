package cloud.file.management.common;

import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.ServerSetting;

import java.io.File;
import java.util.List;

public class DeleteFiles extends Message {
    public DeleteFiles(String login, List<String> list){
        super(login, list);
    }

    @Override
    public void preprocess() {
        System.out.println("usuwam na serwerze te pliki:");
        LambdaExpression.consumer(getList(),
                s->{
                    System.out.println(ServerSetting.getPathToUserResources().toString()+"/"+getLogin()+"/"+s);
                    File file = new File(ServerSetting.getPathToUserResources().toString()+"/"+getLogin()+"/"+s);
                    if(file.delete())
                        System.out.println("File deleted successfully");
                    else
                        System.out.println("Failed to delete the file");
                }
            );
    }
}
