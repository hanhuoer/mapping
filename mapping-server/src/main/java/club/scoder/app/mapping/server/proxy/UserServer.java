package club.scoder.app.mapping.server.proxy;

import club.scoder.app.mapping.common.Server;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.filter.ClientHttpRequestFilter;
import club.scoder.app.mapping.server.handler.TrafficHandler;
import club.scoder.app.mapping.server.handler.UserChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserServer implements Server {

    private final List<Integer> proxyPortList;
    private final ServerContext serverContext;
    private final ClientHttpRequestFilter clientHttpRequestFilter;
    private EventLoopGroup BOSS_GROUP;
    private EventLoopGroup WORK_GROUP;


    public UserServer(ServerContext serverContext, ClientHttpRequestFilter clientHttpRequestFilter) {
        this.serverContext = serverContext;
        proxyPortList = serverContext.getProxyPortList();
        this.clientHttpRequestFilter = clientHttpRequestFilter;

    }

    @Override
    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        BOSS_GROUP = new NioEventLoopGroup(1);
        WORK_GROUP = new NioEventLoopGroup(32);
        serverBootstrap.group(BOSS_GROUP, WORK_GROUP)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addFirst(new TrafficHandler(serverContext.getByteTrafficHandler()));
                        ch.pipeline().addLast(new UserChannelHandler(serverContext, clientHttpRequestFilter));
                    }
                });
        for (Integer port : proxyPortList) {
            try {
                serverBootstrap.bind(port).sync();
                log.info("proxy bind port: {}", port);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        BOSS_GROUP.shutdownGracefully();
        WORK_GROUP.shutdownGracefully();
    }

    @Override
    public void refresh() {
        stop();
        start();
    }

}
