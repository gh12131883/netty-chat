package m.portfolio.nettychat.socket.converter;

import java.nio.ByteBuffer;

public class ByteConverter {
    public static byte[] convert(String body) {
        byte[] lengthBytes = ByteBuffer.allocate(4).putInt(body.length()).array();

        // Convert the body to bytes
        byte[] bodyBytes = body.getBytes();

        // Create the result byte array
        byte[] resultByteArray = new byte[lengthBytes.length + bodyBytes.length];

        // Copy lengthBytes and bodyBytes into resultByteArray
        System.arraycopy(lengthBytes, 0, resultByteArray, 0, lengthBytes.length);
        System.arraycopy(bodyBytes, 0, resultByteArray, lengthBytes.length, bodyBytes.length);

        return resultByteArray;
    }
}
