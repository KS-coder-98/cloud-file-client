package cloud.file.management.common;

import cloud.file.management.server.model.serverTasks.LoadUserResources;


public class ListUserMessage extends Message {
    public ListUserMessage(String login, String path, long id) {
        super(login, path, null, id);
    }

    public ListUserMessage() {
        super.setList(LoadUserResources.loadListUser());
    }

    @Override
    public void preprocess() {
        //nothing to do
    }
}
