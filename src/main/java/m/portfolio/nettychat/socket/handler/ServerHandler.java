package m.portfolio.nettychat.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import m.portfolio.nettychat.socket.converter.ByteConverter;

import java.nio.charset.Charset;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { // (2)
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset()); // (3)

        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열 [");
        builder.append(readMessage);  // (4)
        builder.append("]");
        log.info(builder.toString());

        // 응답으로 돌려주기
        ByteBuf msgBuffer = Unpooled.buffer();
        String response = "message arrived successfully";
        msgBuffer.writeBytes(ByteConverter.convert(response));
        ctx.write(msgBuffer);
//        msgBuffer.writeBytes("Server Response => received data : ".getBytes());
//
//        ctx.write(msgBuffer); // (5)
//        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception { // (6)
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("error occurs");
        cause.printStackTrace();
        ctx.close();
    }
}
