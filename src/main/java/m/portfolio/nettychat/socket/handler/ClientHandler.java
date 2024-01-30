package m.portfolio.nettychat.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import m.portfolio.nettychat.socket.converter.ByteConverter;

import java.nio.charset.Charset;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) { // (1)
        String sendMessage = "Hello Netty";

        ByteBuf msgBuffer = Unpooled.buffer();
        msgBuffer.writeBytes(ByteConverter.convert(sendMessage));
//        msgBuffer.writeBytes(sendMessage.getBytes());

        StringBuilder builder = new StringBuilder();
        builder.append("Client 전송한 문자열[");
        builder.append(sendMessage);
        builder.append("]");

        log.info(builder.toString());

        ctx.writeAndFlush(msgBuffer);  // (2) 중요
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (3)
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset()); // (4)

        StringBuilder builder = new StringBuilder();
        builder.append("Client 수신한 문자열[");
        builder.append(readMessage);
        builder.append("]");

        log.info(builder.toString());

    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx)   { // (5)
//        ctx.close(); // (6)
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)   {
        cause.printStackTrace();
        ctx.close();
    }
}