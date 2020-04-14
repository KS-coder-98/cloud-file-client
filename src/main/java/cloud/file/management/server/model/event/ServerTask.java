package cloud.file.management.server.model.event;

import cloud.file.management.common.Message;
import cloud.file.management.common.RequestForFileMessage;
import cloud.file.management.server.model.HandlerResources;
import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.communication.EchoClientHandler;
import cloud.file.management.server.model.communication.EchoMultiServer;

import java.util.List;

public abstract class ServerTask {

    public static void makeRequestForFile(List<String> list, String login){
        List<String> listFileInServer = HandlerResources.listNameFile(login);
        list.removeAll(listFileInServer);;
        for ( var s : list ){
            System.out.println(s);
        }
        Message msg = new RequestForFileMessage(login, list);

        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                t->t.getReceive().getMsgList().add(msg),
                t->t.getLogin().equals(login)
        );
//        for( var handlerClient : EchoMultiServer.getListUser() ){
//            if ( handlerClient.getLogin().equals(login) ){
//                handlerClient.getReceive().getMsgList().add(msg);
//            }
//        }
    }



}
