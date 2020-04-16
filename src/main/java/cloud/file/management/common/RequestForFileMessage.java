package cloud.file.management.common;

import cloud.file.management.server.model.FileAPI;
import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.ServerSetting;
import cloud.file.management.server.model.communication.EchoMultiServer;

import java.nio.file.Path;
import java.util.List;

public class RequestForFileMessage extends Message {
    public RequestForFileMessage(String login, List<String> list){
        super(login, list);
    }


    @Override
    public void preprocess() {
        System.out.println("preprocess RequestForFile "+getList());
        for (var relativePath : getList()) {
            Path absolutePath = Path.of(ServerSetting.getPathToUserResources().toString()+"\\"+getLogin()+"\\"+relativePath);
            System.out.println("serwer ma wyslac te pliki: ");
            Message msg = new FileMessage(getLogin(), relativePath, getLogin(), FileAPI.getStreamFile(absolutePath));
            LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                    t->t.getReceive().getMsgList().add(msg),
                    t->t.getLogin().equals(getLogin())
            );
        }
    }
}
