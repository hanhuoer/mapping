package club.scoder.app.mapping.common.handler;

import club.scoder.app.mapping.common.protocol.Message;
import club.scoder.app.mapping.common.protocol.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdleHandler extends IdleStateHandler {

    public IdleHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT == evt) {
            log.warn("channel read timeout {}", ctx.channel());
            ctx.close();
        } else if (IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT == evt) {
            log.warn("channel write timeout {}", ctx.channel());
            Message proxyMessage = new Message();
            proxyMessage.setType(MessageType.HEARTBEAT);
            ctx.channel().writeAndFlush(proxyMessage);
        } else {
            // else
        }
        super.channelIdle(ctx, evt);
    }
}
