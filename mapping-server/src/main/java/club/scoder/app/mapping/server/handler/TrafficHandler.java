package club.scoder.app.mapping.server.handler;

import club.scoder.app.mapping.common.monitor.ByteTrafficHandler;
import club.scoder.app.mapping.common.monitor.Traffic;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class TrafficHandler extends ChannelDuplexHandler {

    private ByteTrafficHandler byteTrafficHandler;


    public TrafficHandler(ByteTrafficHandler byteTrafficHandler) {
        this.byteTrafficHandler = byteTrafficHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.localAddress();
        int port = inetSocketAddress.getPort();
        Traffic traffic = byteTrafficHandler.get(port);
        log.debug("traffic active: {}", traffic);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int port = getPort(ctx);
        int size = getSize(msg);
        Traffic traffic = byteTrafficHandler.get(port);
        byteTrafficHandler.addIn(port, size);
        byteTrafficHandler.increaseIn(port);
        log.debug("traffic in: {}", traffic);
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        int port = getPort(ctx);
        int size = getSize(msg);
        Traffic traffic = byteTrafficHandler.get(port);
        byteTrafficHandler.addOut(port, size);
        byteTrafficHandler.increaseOut(port);
        log.debug("traffic out: {}", traffic);
        super.write(ctx, msg, promise);
    }

    private int getSize(Object msg) {
        ByteBuf message = (ByteBuf) msg;
        return message.readableBytes();
    }

    private int getPort(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.localAddress();
        return inetSocketAddress.getPort();
    }

}
