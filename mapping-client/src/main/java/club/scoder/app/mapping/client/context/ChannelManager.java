package club.scoder.app.mapping.client.context;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.util.Map;

public class ChannelManager {

    /**
     * real server channel.
     * <p>
     * key: user channel id
     * value: real server channel
     */
    public static final Map<String, Channel> REAL_SERVER_CHANNEL_MAP = Maps.newHashMap();

}
