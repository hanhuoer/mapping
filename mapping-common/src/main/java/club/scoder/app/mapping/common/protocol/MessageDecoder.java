package club.scoder.app.mapping.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    private static final int MAX_FRAME_LENGTH = 1 << 21;
    private static final int LENGTH_FIELD_OFFSET = 0;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;
    private static final int MAGIC_LENGTH = 1;
    private static final int VERSION_LENGTH = 1;


    public MessageDecoder() {
        this(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Message decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decodeIn = (ByteBuf) super.decode(ctx, in);

        if (decodeIn == null) return null;
        if (decodeIn.readableBytes() < (MAGIC_LENGTH + VERSION_LENGTH)) return null;
        int frameLength = decodeIn.readInt();
        if (frameLength != decodeIn.readableBytes()) return null;

        byte magic = decodeIn.readByte();
        byte version = decodeIn.readByte();
        byte clientIdLength = decodeIn.readByte();
        byte[] clientId = new byte[clientIdLength];
        if (clientIdLength > 0) {
            decodeIn.readBytes(clientId);
        }
        byte channelIdLength = decodeIn.readByte();
        byte[] channelId = new byte[channelIdLength];
        if (channelIdLength > 0) {
            decodeIn.readBytes(channelId);
        }
        byte inetAddressLength = decodeIn.readByte();
        byte[] inetAddress = new byte[inetAddressLength];
        if (inetAddressLength > 0) {
            decodeIn.readBytes(inetAddress);
        }
        byte type = decodeIn.readByte();
        byte[] data = new byte[decodeIn.readableBytes()];
        decodeIn.readBytes(data);

        Message message = new Message();
        message.setMagic(magic);
        message.setVersion(version);
        message.setClientId(clientId);
        message.setChannelId(channelId);
        message.setInetAddress(inetAddress);
        message.setType(type);
        message.setData(data);
        return message;
    }

}
