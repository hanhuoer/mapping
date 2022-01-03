package club.scoder.app.mapping.server.handler;

import club.scoder.app.mapping.common.protocol.Message;
import club.scoder.app.mapping.common.protocol.MessageType;
import club.scoder.app.mapping.server.context.ChannelManager;
import club.scoder.app.mapping.server.context.ServerContext;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MappingServerHandler extends SimpleChannelInboundHandler<Message> {

    private final ServerContext serverContext;


    public MappingServerHandler(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("client inactive, channel disconnect.");
        String channelId = getChannelId(ctx);
        String clientId = ChannelManager.channelIdClientIdMap.get(channelId);
        ChannelManager.channelIdClientIdMap.remove(channelId);
        ChannelManager.clientIdChannelMap.remove(clientId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception occurred: {}", cause.getMessage());
        ctx.close();
        String channelId = getChannelId(ctx);
        String clientId = ChannelManager.channelIdClientIdMap.get(channelId);
        ChannelManager.channelIdClientIdMap.remove(channelId);
        ChannelManager.clientIdChannelMap.remove(clientId);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        byte type = message.getType();
        log.info("received message, type: {}", type);
        switch (type) {
            case MessageType.REGISTER:
                handlerRegister(ctx, message);
                break;
            case MessageType.DISCONNECTION:
                handlerDisconnection(ctx, message);
                break;
            case MessageType.TRANSMISSION:
                handlerTransmission(ctx, message);
                break;
            case MessageType.HEARTBEAT:
                handlerHeartbeat(ctx, message);
                break;
            default:
                break;
        }
    }

    private void handlerHeartbeat(ChannelHandlerContext ctx, Message message) {
        log.info("channel id: {}, heartbeat.", getChannelId(ctx));
    }

    /**
     * <pre>
     *     Receive a message from the real server.
     *
     * +-------------------+  >>>>>>>>>>>>>>>>  +-------------+
     * | RealServerHandler |        data        | ProxyServer |
     * +-------------------+  >>>>>>>>>>>>>>>>  +-------------+
     *                          proxyChannel
     * </pre>
     *
     * <pre>
     *     Forward raw response to user server.
     *
     * +-------------+  >>>>>>>>>>>>>>>>  +------------+
     * | ProxyServer |        data        | UserServer |
     * +-------------+  >>>>>>>>>>>>>>>>  +------------+
     *                     userChannel
     * </pre>
     *
     * @param ctx     proxy channel handler context
     * @param message message
     */
    private void handlerTransmission(ChannelHandlerContext ctx, Message message) {
        log.info("proxy channel received a message from real service, now ready forward it to user.");
        String channelId = new String(message.getChannelId());
        Channel userChannel = ChannelManager.userChannelMap.get(channelId);
        userChannel.writeAndFlush(Unpooled.copiedBuffer(message.getData()));
    }

    /**
     * Close the user channel when disconnected real server from proxy client.
     *
     * @param ctx     proxy channel handler context
     * @param message message
     */
    private void handlerDisconnection(ChannelHandlerContext ctx, Message message) {
        String channelId = new String(message.getChannelId());
        Channel userChannel = ChannelManager.userChannelMap.get(channelId);
        if (userChannel != null) {
            userChannel.close();
            ChannelManager.userChannelMap.remove(channelId);
        }
        log.info("real service channel closed, and now close user channel.");
    }

    private void handlerRegister(ChannelHandlerContext ctx, Message message) {
        String clientId = new String(message.getClientId(), StandardCharsets.UTF_8);
        String channelId = getChannelId(ctx);
        if (!serverContext.auth(clientId)) {
            Message msg = new Message();
            msg.setType(MessageType.REGISTER_FAILURE);
            ctx.writeAndFlush(msg);
            log.warn("client id: {} client is not exist.", clientId);
        } else if (ChannelManager.clientIdChannelMap.containsKey(clientId) || ChannelManager.channelIdClientIdMap.containsKey(channelId)) {
            Message msg = new Message();
            msg.setType(MessageType.REGISTER_REPEAT);
            ctx.writeAndFlush(msg);
            log.warn("client id: {} has been registered, please don't repeat the operate.", clientId);
        } else {
            Channel channel = ctx.channel();
            ChannelManager.channelIdClientIdMap.put(channelId, clientId);
            ChannelManager.clientIdChannelMap.put(clientId, channel);
            Message msg = new Message();
            msg.setType(MessageType.REGISTER_SUCCESS);
            ctx.writeAndFlush(msg);
            log.info("client: {}, channel: {} register successful.", clientId, getChannelId(ctx));
        }
    }

    private String getChannelId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

}
