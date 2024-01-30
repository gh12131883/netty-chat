package m.portfolio.nettychat.socket.manager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import m.portfolio.nettychat.socket.config.CustomLengthFieldBasedFrameDecoder;
import m.portfolio.nettychat.socket.handler.ServerHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServerInitializer extends BaseSocketInitializer {
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    private final int nBossThread = 1;

    @Override
    public void start() throws InterruptedException {
        this.bossGroup = new NioEventLoopGroup(nBossThread);
        this.workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() { // 4
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(CustomLengthFieldBasedFrameDecoder.getInstance());
                        p.addLast(new ServerHandler()); //1
                    }
                });

        ChannelFuture f = b.bind(8888).sync();

        log.info("server is setup");

        f.channel().closeFuture().sync();

        this.shutdown();
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}