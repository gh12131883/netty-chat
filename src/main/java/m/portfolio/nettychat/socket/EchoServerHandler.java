package m.portfolio.nettychat.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { // (2)

        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset()); // (3)

        StringBuilder builder = new StringBuilder();
        builder.append("수신한 문자열 [");
        builder.append(readMessage);  // (4)
        builder.append("]");
        System.out.println(builder.toString());

        // 응답으로 돌려주기
        ByteBuf msgBuffer = Unpooled.buffer();
        msgBuffer.writeBytes("Server Response => received data : ".getBytes());

        ctx.write(msgBuffer); // (5)
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception { // (6)
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("오류 발생");
        cause.printStackTrace();
        ctx.close();
    }
}
