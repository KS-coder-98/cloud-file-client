package cloud.file.management.common;

import cloud.file.management.server.model.event.ServerTask;

import java.util.List;

public class ListLocalFileMessage extends Message {

    public ListLocalFileMessage(String login, List<String> list){
        super(login, list);
    }

    @Override
    public void preprocess() {
        System.out.println("otrzymano listlocalfilemessafge");
        ServerTask.makeRequestForFile(getList(), getLogin());
    }
}
