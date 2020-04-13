package cloud.file.management.common;

import cloud.file.management.server.model.serverTasks.LoadUserResources;


public class ListUserMessage extends Message {
    public ListUserMessage(String login, String path, byte[] fileInByte) {
        super(login, path, null, fileInByte);
    }

    public ListUserMessage() {
        super.setList(LoadUserResources.loadListUser());
    }

    @Override
    public void preprocess() {
        //nothing to do
    }
}
