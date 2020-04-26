package cloud.file.management.common;

import cloud.file.management.server.model.LambdaExpression;
import cloud.file.management.server.model.communication.EchoMultiServer;

import java.util.List;

/**
 * This class extend Message class. Target this class is processed request for file.
 */
public class RequestForFileMessage extends Message {
    public RequestForFileMessage(String login, List<String> list){
        super(login, list);
    }


    @Override
    public void preprocess() {
        System.out.println("preprocess RequestForFile "+getList());
        for (var relativePath : getList()) {
            Message msg = new FileMessage(getLogin(), relativePath, "", getId());
            LambdaExpression.actionIf(EchoMultiServer.getListUser(),
                    t->t.getSend().getMsgList().add(msg),
                    t->t.getLogin().equals(getLogin())
            );
        }
    }
}
