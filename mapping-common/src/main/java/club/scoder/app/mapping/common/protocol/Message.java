package club.scoder.app.mapping.common.protocol;

import lombok.Data;

@Data
public class Message {

    private byte magic;
    private byte version;
    private byte[] clientId;
    private byte[] channelId;
    private byte[] inetAddress;
    private byte type;
    private byte[] data;

}
