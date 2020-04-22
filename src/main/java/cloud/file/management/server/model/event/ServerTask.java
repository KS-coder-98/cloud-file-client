package cloud.file.management.server.model.event;

import cloud.file.management.common.ListLocalFileMessage;
import cloud.file.management.common.ListUserMessage;
import cloud.file.management.common.Message;
import cloud.file.management.common.RequestForFileMessage;
import cloud.file.management.server.model.HandlerResources;
import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.communication.EchoMultiServer;

import java.util.List;

public abstract class ServerTask {

    public static void makeRequestForFile(List<String> list, String login){
        System.out.println("run makeRequestForFile");
        List<String> listFileInServer = HandlerResources.listNameFile(login);
        list.removeAll(listFileInServer);;
        Message msg = new RequestForFileMessage(login, list);
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                t->t.getSend().getMsgList().add(msg),
                t->t.getLogin().equals(login)
        );
        ServerTask.sendListFileUser(login);
        System.out.println("end makeRequestForFile");
    }

    public static void sendListFileUser(String login){
        System.out.println("run sendListFileUser");
        var listFile = HandlerResources.listNameFile(login);
        Message msg = new ListLocalFileMessage(login, listFile);
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                t->t.getReceive().getMsgList().add(msg),
                t->t.getLogin().equals(login)
        );
        for ( var s : EchoMultiServer.getListUser() ){
            System.err.println(s.getReceive().getMsgList().toString());
        }
        System.out.println("end sendListFileUser");
    }

    public static void sendListUserName(){
        Message msg = new ListUserMessage();
        LambdaExpression.consumer(EchoMultiServer.getListUser(),
                t->t.getReceive().getMsgList().add(msg)
        );
    }
}
