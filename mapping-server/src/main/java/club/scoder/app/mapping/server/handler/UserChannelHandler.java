package club.scoder.app.mapping.server.handler;

import club.scoder.app.mapping.common.protocol.Message;
import club.scoder.app.mapping.common.protocol.MessageType;
import club.scoder.app.mapping.server.context.ChannelManager;
import club.scoder.app.mapping.server.context.ServerContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UserChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final ServerContext serverContext;


    public UserChannelHandler(ServerContext serverContext) {
        this.serverContext = serverContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelManager.userChannelMap.put(getChannelId(ctx), ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // user channel is closed.
        ChannelManager.userChannelMap.remove(getChannelId(ctx));
        Message message = new Message();
        message.setType(MessageType.DISCONNECTION);
        sendToProxyChannel(message, getProxyChannel(ctx));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        ChannelManager.userChannelMap.remove(getChannelId(ctx));
    }

    /**
     * Received a message from user, ready to send it to proxy client.
     *
     * @param ctx ctx
     * @param msg msg
     * @throws Exception e
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Message message = new Message();
        message.setType(MessageType.TRANSMISSION);
        message.setChannelId(ctx.channel().id().asLongText().getBytes(StandardCharsets.UTF_8));
        byte[] inetAddress = (getTargetInet(ctx).getBytes(StandardCharsets.UTF_8));
        message.setInetAddress(inetAddress);
        message.setData(ByteBufUtil.getBytes(msg));
        sendToProxyChannel(message, getProxyChannel(ctx));
    }

    /**
     * <pre>
     *     Send a message to the proxy client
     * +-------------+  >>>>>>>>>>>>>>>>  +-------------+
     * | UserChannel |        data        | ProxyClient |
     * +-------------+  >>>>>>>>>>>>>>>>  +-------------+
     * </pre>
     *
     * @param message      message, from user
     * @param proxyChannel proxy channel
     */
    private void sendToProxyChannel(Message message, Channel proxyChannel) {
        if (proxyChannel != null && proxyChannel.isWritable()) {
            proxyChannel.writeAndFlush(message);
            log.info("send data success; channel: {}", proxyChannel.id().asLongText());
        } else {
            log.info("send data failed; proxy channel is not exist.");
        }
    }

    private String getChannelId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

    private int getUserPort(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();
        return inetSocketAddress.getPort();
    }

    private String getClientId(ChannelHandlerContext ctx) {
        int userPort = this.getUserPort(ctx);
        return serverContext.getClientIdByProxyPort(userPort);
    }

    private String getTargetInet(ChannelHandlerContext ctx) {
        int userPort = this.getUserPort(ctx);
        InetSocketAddress inetSocketAddress = serverContext.getClientInetByProxyPort(userPort);
        return inetSocketAddress.getHostString() + ":" + inetSocketAddress.getPort();
    }

    private Channel getProxyChannel(ChannelHandlerContext ctx) {
        String clientId = this.getClientId(ctx);
        return ChannelManager.clientIdChannelMap.get(clientId);
    }

}
