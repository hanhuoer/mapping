package club.scoder.app.mapping.client.proxy;

import club.scoder.app.mapping.client.context.ClientConfiguration;
import club.scoder.app.mapping.client.context.ClientContext;
import club.scoder.app.mapping.client.handler.IdleClientHandler;
import club.scoder.app.mapping.client.handler.MappingClientHandler;
import club.scoder.app.mapping.common.Retry;
import club.scoder.app.mapping.common.Server;
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
import java.util.concurrent.TimeUnit;

@Slf4j
public class MappingClient implements Server {

    private final String clientId;
    private final String serverHost;
    private final int serverPort;
    private final Bootstrap bootstrap;
    private final EventLoopGroup workGroup;
    private final Retry retry;


    public MappingClient(ClientContext clientContext) {
        ClientConfiguration configuration = clientContext.getConfiguration();
        clientId = configuration.getClientId();
        serverHost = configuration.getServerHost();
        serverPort = configuration.getServerPort();

        bootstrap = new Bootstrap();
        workGroup = new NioEventLoopGroup();
        retry = new Retry();

        bootstrap.group(workGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new MessageDecoder());
                ch.pipeline().addLast(new MessageEncoder());
                ch.pipeline().addLast(new IdleClientHandler());
                ch.pipeline().addLast(new MappingClientHandler());
            }
        });
    }

    @Override
    public void start() {
        connect();
    }

    @Override
    public void stop() {
        workGroup.shutdownGracefully();
    }

    private void reconnect() {
        long ms = retry.waitMilliseconds();
        try {
            if (ms > 0) {
                log.info("[reconnect] retries: {}, wait ms: {}", retry.getRetries(), ms);
                TimeUnit.MILLISECONDS.sleep(ms);
                connect();
            } else {
                log.warn("[reconnect] retries exceeded.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
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
                    reconnect();
                }
            }
        });
    }

}
