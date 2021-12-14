package club.scoder.app.mapping.client.handler;

import club.scoder.app.mapping.client.context.ChannelManager;
import club.scoder.app.mapping.common.protocol.Message;
import club.scoder.app.mapping.common.protocol.MessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MappingClientHandler extends SimpleChannelInboundHandler<Message> {

    private final NioEventLoopGroup WORK_GROUP;


    public MappingClientHandler() {
        WORK_GROUP = new NioEventLoopGroup();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception occurred: {}", cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        byte type = msg.getType();
        log.info("received message, type: {}", type);
        switch (type) {
            case MessageType.REGISTER_SUCCESS:
                handlerRegisterSuccess(ctx, msg);
                break;
            case MessageType.REGISTER_FAILURE:
                handlerRegisterFailure(ctx, msg);
                break;
            case MessageType.DISCONNECTION:
                handlerDisconnection(ctx, msg);
                break;
            case MessageType.TRANSMISSION:
                handlerTransmission(ctx, msg);
                break;
            case MessageType.HEARTBEAT:
                handlerHeartbeat(ctx, msg);
                break;
            default:
                break;
        }
    }

    private void handlerHeartbeat(ChannelHandlerContext ctx, Message msg) {
        log.debug("channel: {} received heartbeat message.", ctx.channel().id().asLongText());
    }

    /**
     * <pre>
     *     Receive a message from user channel that will be used to send to real server.
     * +-------------+  >>>>>>>>>>>>>>>>  +---------------+
     * | UserChannel |        data        | MappingClient |
     * +-------------+  >>>>>>>>>>>>>>>>  +---------------+
     *                   MappingChannel
     * </pre>
     *
     * @param ctx mapping channel
     * @param msg message from mapping channel(user channel)
     */
    private void handlerTransmission(ChannelHandlerContext ctx, Message msg) {
        log.info("proxy channel received a message from user channel, now ready forward it to real service.");
        ByteBuf buffer = Unpooled.copiedBuffer(msg.getData());

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(WORK_GROUP).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RealServerHandler(ctx, msg.getChannelId()));
            }
        });
        String inet = new String(msg.getInetAddress());
        String[] inetSplit = inet.split(":");
        String host = inetSplit[0];
        int port = Integer.parseInt(inetSplit[1]);
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.channel().writeAndFlush(buffer);
        } catch (InterruptedException e) {
            log.error("an exception occurred while transferring data to real service: {}", e.getMessage());
            disconnectUserChannel(ctx);
        }
    }

    /**
     * Failed to establish connection with the real server, notify the proxy server to close user channel.
     *
     * @param ctx mapping channel
     */
    private void disconnectUserChannel(ChannelHandlerContext ctx) {
        log.info("failed establish connect to real service, now notify proxy server.");
        Message msg = new Message();
        msg.setType(MessageType.DISCONNECTION);
        ctx.writeAndFlush(msg);
    }

    /**
     * Close the connection to the real server when the connection to user is disconnected.
     *
     * @param ctx mapping channel
     * @param msg message from mapping server
     */
    private void handlerDisconnection(ChannelHandlerContext ctx, Message msg) {
        String userChannelId = ctx.channel().id().asLongText();
        Channel proxiedChannel = ChannelManager.REAL_SERVER_CHANNEL_MAP.get(userChannelId);
        if (proxiedChannel != null) {
            proxiedChannel.close();
            ChannelManager.REAL_SERVER_CHANNEL_MAP.remove(userChannelId);
        }
        log.info("user channel disconnected, and now close real service channel.");
    }

    /**
     * Authorization failed.
     *
     * @param ctx mapping channel
     * @param msg message from mapping server
     */
    private void handlerRegisterFailure(ChannelHandlerContext ctx, Message msg) {
        log.info("authorization failed.");
        ctx.close();
    }

    /**
     * Authorization succeeded.
     *
     * @param ctx mapping channel
     * @param msg message from mapping server
     */
    private void handlerRegisterSuccess(ChannelHandlerContext ctx, Message msg) {
        log.info("authorization succeeded.");
    }

}
