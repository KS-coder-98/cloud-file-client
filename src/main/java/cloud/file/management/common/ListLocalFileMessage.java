package cloud.file.management.common;

import cloud.file.management.server.model.event.ServerTask;

import java.util.List;

/**
 * This class extend Message class. Target this class give or processed information about local user's files
 */
public class ListLocalFileMessage extends Message {

    public ListLocalFileMessage(String login, List<String> list){
        super(login, list);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess ListLocalFileMessage");
        ServerTask.makeRequestForFile(getList(), getLogin());
        ServerTask.sendListUserName();
    }
}
