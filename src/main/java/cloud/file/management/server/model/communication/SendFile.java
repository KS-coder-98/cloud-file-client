package cloud.file.management.server.model.communication;


import cloud.file.management.server.model.Convert;
import cloud.file.management.server.model.ServerSetting;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SendFile {
    private OutputStream out;

    public SendFile(OutputStream out) {
        this.out = out;
    }

    public void sendFile(Path path, long id, String login) {
        try {
            //send id
            byte[] idBytes = Convert.longToBytes(id);
            out.write(idBytes);

            //send size
            FileChannel fileChannel = FileChannel.open(Paths.get(ServerSetting.getPathToUserResources() + "\\" + login + "\\" + path));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
