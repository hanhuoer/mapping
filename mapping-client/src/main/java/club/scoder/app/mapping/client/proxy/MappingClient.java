package club.scoder.app.mapping.client.proxy;

import club.scoder.app.mapping.client.context.ClientConfiguration;
import club.scoder.app.mapping.client.context.ClientContext;
import club.scoder.app.mapping.client.handler.MappingClientHandler;
import club.scoder.app.mapping.common.Server;
import club.scoder.app.mapping.common.handler.IdleHandler;
import club.scoder.app.mapping.common.protocol.Message;
import club.scoder.app.mapping.common.protocol.MessageDecoder;
import club.scoder.app.mapping.common.protocol.MessageEncoder;
import club.scoder.app.mapping.common.protocol.MessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MappingClient implements Server {

    private final String clientId;
    private final String serverHost;
    private final int serverPort;
    private final Bootstrap bootstrap;
    private final EventLoopGroup workGroup;


    public MappingClient(ClientContext clientContext) {
        ClientConfiguration configuration = clientContext.getConfiguration();
        clientId = configuration.getClientId();
        serverHost = configuration.getServerHost();
        serverPort = configuration.getServerPort();

        bootstrap = new Bootstrap();
        workGroup = new NioEventLoopGroup();

        bootstrap.group(workGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new MessageDecoder());
                ch.pipeline().addLast(new MessageEncoder());
                ch.pipeline().addLast(new IdleHandler(60, 30, 0));
                ch.pipeline().addLast(new MappingClientHandler());
            }
        });
    }

    @Override
    public void start() {
        bootstrap.connect(serverHost, serverPort).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("client: {} connection succeed.", clientId);
                    Message msg = new Message();
                    msg.setType(MessageType.REGISTER);
                    msg.setClientId(clientId.getBytes(StandardCharsets.UTF_8));
                    channelFuture.channel().writeAndFlush(msg);
                } else {
                    log.info("client: {} connection failed.", clientId);
                }
            }
        });
    }

    @Override
    public void stop() {
        workGroup.shutdownGracefully();
    }

}
