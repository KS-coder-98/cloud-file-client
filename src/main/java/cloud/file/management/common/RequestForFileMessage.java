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
            System.out.println("serwer ma wyslac te pliki: ");
            //todo wysyłanie plikow do klijenta
            Message msg = new FileMessage(getLogin(), relativePath, "", getId());
            LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                    t->t.getSend().getMsgList().add(msg),
                    t->t.getLogin().equals(getLogin())
            );
        }
    }
}
