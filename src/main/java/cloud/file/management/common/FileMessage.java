package cloud.file.management.common;

import cloud.file.management.server.model.FileAPI;

import java.nio.file.Path;

public class FileMessage extends Message {
    public FileMessage() {
        super();
    }

    public FileMessage(String login, String path, String pathDst, byte[] fileInByte) {
        super(login, path, pathDst, fileInByte);
    }

    @Override
    public void preprocess() {
        FileAPI.saveFile(getLogin(), getPath(), getPathDst(), getFileInByte());
    }
}
