package m.portfolio.nettychat.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import m.portfolio.nettychat.socket.handler.ClientHandler;
import org.springframework.stereotype.Component;

@Component
public class TestClient extends BaseSocket{
    private EventLoopGroup group = null;

    @Override
    public void setup() throws InterruptedException {
        this.group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ClientHandler());
                    }
                });

        ChannelFuture f = b.connect("localhost", 8888).sync();

        f.channel().closeFuture().sync();
    }

    @Override
    public void shutdown() {
        this.group.shutdownGracefully();
    }
}