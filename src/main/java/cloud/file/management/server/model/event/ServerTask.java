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
        List<String> listFileInServer = HandlerResources.listNameFile(login);
        list.removeAll(listFileInServer);;
        Message msg = new RequestForFileMessage(login, list);
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                t->t.getReceive().getMsgList().add(msg),
                t->t.getLogin().equals(login)
        );
                ServerTask.sendListFileUser(login);
    }

    public static void sendListFileUser(String login){
        var listFile = HandlerResources.listNameFile(login);
        System.out.println("lista plikow na serwerze dla "+login+" "+listFile);
        Message msg = new ListLocalFileMessage(login, listFile);
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                t->t.getReceive().getMsgList().add(msg),
                t->t.getLogin().equals(login)
        );
        System.out.println("lista użytkownikow na serwerze: " + EchoMultiServer.getListUser());
        for ( var s : EchoMultiServer.getListUser() ){
            System.err.println(s.getReceive().getMsgList().toString());
        }
    }

    public static void sendListUserName(){
        Message msg = new ListUserMessage();
        LambdaExpression.consumer(EchoMultiServer.getListUser(),
                t->t.getReceive().getMsgList().add(msg)
        );
    }
}
