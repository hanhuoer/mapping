package club.scoder.app.mapping.common.protocol;

public class MessageType {

    public static final byte REGISTER = 0x01;
    public static final byte REGISTER_SUCCESS = 0x02;
    public static final byte REGISTER_FAILURE = 0x03;
    public static final byte REGISTER_REPEAT = 0x04;
    public static final byte DISCONNECTION = 0x05;
    public static final byte HEARTBEAT = 0x06;
    public static final byte TRANSMISSION = 0x07;

}
