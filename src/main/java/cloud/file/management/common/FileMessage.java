package cloud.file.management.common;

import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.communication.EchoClientHandler;
import cloud.file.management.server.model.communication.EchoMultiServer;

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
        System.out.println("nazwa "+ this.getPath());
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                (EchoClientHandler e)->e.getReceive().getMsgList().add(this),
                echoClientHandler -> echoClientHandler.getLogin().equals(getLogin()));
    }

}
