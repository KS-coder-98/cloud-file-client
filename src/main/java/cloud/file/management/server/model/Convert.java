package cloud.file.management.server.model;

import java.nio.ByteBuffer;

/**
 *  Class implements converting byte[] to long and long to byte[]
 */
public abstract class Convert {
    /**
     * Convert value type long to array byte
     *
     * @param x value to convert to array byte
     * @return Return is array of byte which present long value
     */
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    /**
     * Convert array byte to long. To correctly work this function the array should be size of type of type long
     *
     * @param bytes bytes to convert
     * @return Return value from convert byte[]
     */
    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}

