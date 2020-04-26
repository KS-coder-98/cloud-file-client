package cloud.file.management.server.model.event;

import cloud.file.management.common.ListLocalFileMessage;
import cloud.file.management.common.ListUserMessage;
import cloud.file.management.common.Message;
import cloud.file.management.common.RequestForFileMessage;
import cloud.file.management.server.model.HandlerResources;
import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.communication.EchoMultiServer;

import java.util.List;

/**
 * Class implements creating: requesting for file, sending file to chose user and sending to all users list with registered user on server
 */
public abstract class ServerTask {

    /**
     * Function generates list of file which doesn't exist in server resources.
     * This fields from list is added to queue which is responsible for buffer sending message
     *
     * @param list To this list this function puts path of file which isn't in server disc
     * @param login user's login is necessary to chose correct path in hard drive
     */
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

    /**
     * Function add to appropriate list message. This message mainly consists of list files which exist in server for chose user
     *
     * @param login login to user which we send message
     */
    public static void sendListFileUser(String login){
        System.out.println("run sendListFileUser");
        var listFile = HandlerResources.listNameFile(login);
        Message msg = new ListLocalFileMessage(login, listFile);
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                t->t.getSend().getMsgList().add(msg),
                t->t.getLogin().equals(login)
        );
//        for ( var s : EchoMultiServer.getListUser() ){
//            System.err.println(s.getSend().getMsgList().toString());
//        }
//        System.out.println("end sendListFileUser");
    }

    /**
     * Function adds message to lists all users. The message mainly consists of list of all users login
     */
    public static void sendListUserName(){
        Message msg = new ListUserMessage();
        LambdaExpression.consumer(EchoMultiServer.getListUser(),
                t->t.getSend().getMsgList().add(msg)
        );
    }
}
