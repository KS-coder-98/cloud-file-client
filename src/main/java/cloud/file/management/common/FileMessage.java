package cloud.file.management.common;

import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.communication.EchoClientHandle;
import cloud.file.management.server.model.communication.EchoMultiServer;

/**
 * This class extend Message class. Target this class is processed metadata for receiving file
 */
public class FileMessage extends Message {
    public FileMessage() {
        super();
    }

    /**
     * @param login user login
     * @param path path to file
     * @param pathDst destination user
     * @param id id file to recognize file
     */
    public FileMessage(String login, String path, String pathDst, long id) {
        super(login, path, pathDst, id);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess FileMessage");
        LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                (EchoClientHandle e)->e.getReceive().getMsgList().add(this),
                echoClientHandle -> echoClientHandle.getLogin().equals(getLogin()));
    }

}
