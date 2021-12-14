package club.scoder.app.mapping.client.handler;

import club.scoder.app.mapping.client.context.ChannelManager;
import club.scoder.app.mapping.common.protocol.Message;
import club.scoder.app.mapping.common.protocol.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final ChannelHandlerContext proxyChannelHandlerContext;
    private final byte[] userChannelId;


    /**
     * Create real server handler
     *
     * @param ctx           proxy channel handler context
     * @param userChannelId user channel id
     */
    public RealServerHandler(ChannelHandlerContext ctx, byte[] userChannelId) {
        this.proxyChannelHandlerContext = ctx;
        this.userChannelId = userChannelId;
    }

    /**
     * After the connection with real server is successful, will open a channel that then associate
     * the user channel(user request) with it.
     *
     * @param ctx real server channel
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelManager.REAL_SERVER_CHANNEL_MAP.put(new String(userChannelId), proxyChannelHandlerContext.channel());
    }

    /**
     * The connection to the real server is disconnected, use proxy channel notify server to disconnect
     * the user channel.
     *
     * @param ctx real server channel
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Message message = new Message();
        message.setType(MessageType.DISCONNECTION);
        proxyChannelHandlerContext.channel().writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     * <pre>
     *     raw response from real server
     * +------------+  >>>>>>>>>>>>>>>>  +-------------------+
     * | RealServer |        data        | RealServerHandler |
     * +------------+  >>>>>>>>>>>>>>>>  +-------------------+
     * </pre>
     *
     * <pre>
     *     send the raw response to proxy server
     * +-------------------+  >>>>>>>>>>>>>>>>  +-------------+
     * | RealServerHandler |        data        | ProxyServer |
     * +-------------------+  >>>>>>>>>>>>>>>>  +-------------+
     * </pre>
     *
     * @param ctx real server channel
     * @param msg real server
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Message message = new Message();
        message.setType(MessageType.TRANSMISSION);
        message.setChannelId(userChannelId);
        message.setData(ByteBufUtil.getBytes(msg));
        proxyChannelHandlerContext.writeAndFlush(message);
    }

}
