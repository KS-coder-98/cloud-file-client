package cloud.file.management.common;

import cloud.file.management.server.model.FileAPI;
import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.ServerSetting;
import cloud.file.management.server.model.communication.EchoClientHandler;
import cloud.file.management.server.model.communication.EchoMultiServer;
import cloud.file.management.server.model.event.ServerTask;

import java.nio.file.Path;

public class FileMessage extends Message {
    public FileMessage() {
        super();
    }

    public FileMessage(String login, String path, String pathDst, long id) {
        super(login, path, pathDst, id);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess FileMessage");
//todo save file !!!!
//        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
//                echoClientHandler -> echoClientHandler.
//        );
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                (EchoClientHandler e)->e.getReceive().getMsgList().add(this),
                echoClientHandler -> echoClientHandler.getLogin().equals(getLogin()));
        System.out.println("#######################################3");
        LambdaExpression.consumer(EchoMultiServer.getListUser(),
                echoClientHandler -> LambdaExpression.consumer(echoClientHandler.getMsgList(),
                        Message::toString));
//        var msg = EchoMultiServer.getListUser().get(0);
//        FileAPI.saveFile(getLogin(), getPath(), getPathDst(), getFileInByte());
    }
}
