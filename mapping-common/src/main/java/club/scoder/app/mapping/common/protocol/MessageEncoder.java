package club.scoder.app.mapping.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageEncoder extends MessageToByteEncoder<Message> {

    private static final byte MAGIC = 0x64;
    private static final byte VERSION = 0x04;
    private static final int MAGIC_LENGTH = 1;
    private static final int VERSION_LENGTH = 1;
    private static final int CLIENT_ID_LENGTH = 1;
    private static final int CHANNEL_ID_LENGTH = 1;
    private static final int INET_ADDRESS_LENGTH = 1;
    private static final int TYPE_LENGTH = 1;


    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
        int frameLength = 0;
        frameLength += MAGIC_LENGTH;
        frameLength += VERSION_LENGTH;
        frameLength += CLIENT_ID_LENGTH;
        byte clientIdLength = 0x00;
        if (message.getClientId() != null) {
            clientIdLength = (byte) message.getClientId().length;
            frameLength += clientIdLength;
        }
        frameLength += CHANNEL_ID_LENGTH;
        byte channelIdLength = 0x00;
        if (message.getChannelId() != null) {
            channelIdLength = (byte) message.getChannelId().length;
            frameLength += channelIdLength;
        }
        frameLength += INET_ADDRESS_LENGTH;
        byte inetAddressLength = 0x00;
        if (message.getInetAddress() != null) {
            inetAddressLength = (byte) message.getInetAddress().length;
            frameLength += inetAddressLength;
        }
        frameLength += TYPE_LENGTH;
        if (message.getData() != null) {
            frameLength += message.getData().length;
        }

        out.writeInt(frameLength);
        out.writeByte(MAGIC);
        out.writeByte(VERSION);
        out.writeByte(clientIdLength);
        if (message.getClientId() != null) out.writeBytes(message.getClientId());
        out.writeByte(channelIdLength);
        if (message.getChannelId() != null) out.writeBytes(message.getChannelId());
        out.writeByte(inetAddressLength);
        if (message.getInetAddress() != null) out.writeBytes(message.getInetAddress());
        out.writeByte(message.getType());
        if (message.getData() != null) out.writeBytes(message.getData());
    }

}
