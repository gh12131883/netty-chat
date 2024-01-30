package m.portfolio.nettychat.socket.config;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class CustomLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder{
    private static CustomLengthFieldBasedFrameDecoder instance;

    private CustomLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public static CustomLengthFieldBasedFrameDecoder getInstance(){
        if(instance == null){
            synchronized (CustomLengthFieldBasedFrameDecoder.class){
                instance = new CustomLengthFieldBasedFrameDecoder(
                        Integer.MAX_VALUE, 0, 4, 0, 4
                );
            }
        }
        return instance;
    }
}
