package cloud.file.management.server.model.communication;


import cloud.file.management.server.model.Convert;
import cloud.file.management.server.model.ServerSetting;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class implements sending file. This class is based on OutputStream.
 */
public class SendFile {
    private OutputStream out;

    /**
     * Create object with bind specified OutputStream
     *
     * @param out stream is responsible for sending file
     */
    public SendFile(OutputStream out) {
        this.out = out;
    }

    /**
     * Function sends file to client.
     * First send 8 bytes are assigned to id. Next send 8 bytes is size file. After that send all file data. For reading
     * file's bytes is used FileChanel class
     *
     * @param path path where is located file to send
     * @param id id file is added to recognize file
     * @param login nick user. It is necessary to find appropriate client
     */
    public void sendFile(Path path, long id, String login) {
        try {
            //send id
            byte[] idBytes = Convert.longToBytes(id);
            out.write(idBytes);

            //send size
            FileChannel fileChannel = FileChannel.open(
                    Paths.get(ServerSetting.getPathToUserResources() + "\\" + login + "\\" + path)
            );
            long size = fileChannel.size();
            byte[] sizeFile = Convert.longToBytes(size);
            out.write(sizeFile);

            //   Allocate a ByteBuffer
            long defaultSizeBuffer = 1024;
            long sizeBuffer = Math.min(size, defaultSizeBuffer);
            ByteBuffer buffer = ByteBuffer.allocate(Math.toIntExact( sizeBuffer));

            long sendBytes = 0, count;
            while ((count = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                out.write(buffer.array());
                buffer.clear();
                sendBytes += count;
                if ((size - sendBytes) < defaultSizeBuffer) {
                    buffer = ByteBuffer.allocate(Math.toIntExact (size - sendBytes));
                }
            }
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
