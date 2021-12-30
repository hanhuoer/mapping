package club.scoder.app.mapping.server.proxy;

import club.scoder.app.mapping.common.Server;
import club.scoder.app.mapping.common.protocol.MessageDecoder;
import club.scoder.app.mapping.common.protocol.MessageEncoder;
import club.scoder.app.mapping.server.context.ServerConfiguration;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.handler.IdleServerHandler;
import club.scoder.app.mapping.server.handler.MappingServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class MappingServer implements Server {

    private final EventLoopGroup BOSS_GROUP;
    private final EventLoopGroup WORK_GROUP;
    private final String hostname;
    private final int port;
    private final ServerContext serverContext;


    public MappingServer(ServerContext serverContext) {
        this.serverContext = serverContext;
        ServerConfiguration configuration = serverContext.getConfiguration();
        hostname = configuration.getServerHost();
        port = configuration.getServerPort();
        BOSS_GROUP = new NioEventLoopGroup(1);
        WORK_GROUP = new NioEventLoopGroup(32);
    }

    @Override
    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(WORK_GROUP, BOSS_GROUP)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MessageDecoder())
                                .addLast(new MessageEncoder())
                                .addLast(new IdleServerHandler())
                                .addLast(new MappingServerHandler(serverContext));
                    }
                });
        ChannelFuture channelFuture = null;
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname, port);
            channelFuture = serverBootstrap.bind(inetSocketAddress).sync();
            log.info("MappingServer started on port: {}.", port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        BOSS_GROUP.shutdownGracefully();
        WORK_GROUP.shutdownGracefully();
    }

}
