package m.portfolio.nettychat.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import m.portfolio.nettychat.socket.handler.ServerHandler;
import org.springframework.stereotype.Component;

@Component
public class TestServerManager extends BaseSocketManager {
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    private final int nBossThread = 1;

    @Override
    public void setup() throws InterruptedException {
        this.bossGroup = new NioEventLoopGroup(nBossThread);
        this.workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() { // 4
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerHandler()); //1
                    }
                });

        ChannelFuture f = b.bind(8888).sync();

        System.out.println("서버시작");

        f.channel().closeFuture().sync();

        this.shutdown();
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}