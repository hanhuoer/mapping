package club.scoder.app.mapping.server.context;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.util.Map;

public class ChannelManager {

    /**
     * key  : channel id
     * value: client id
     */
    public static Map<String, String> channelIdClientIdMap = Maps.newHashMap();
    /**
     * <pre>
     *     The connection channel between the proxy server and the proxy client.
     *
     * +-------------+ >>>>>>>>>>>>>>> +-------------+
     * | ProxyServer |  proxy channel  | ProxyClient |
     * +-------------+ >>>>>>>>>>>>>>> +-------------+
     * </pre>
     * key  : client id
     * value: proxy channel
     */
    public static Map<String, Channel> clientIdChannelMap = Maps.newHashMap();
    /**
     * 用户向代理端口发送请求的缓存
     * <p>
     * key: channel id
     * value: channel
     */
    public static Map<String, Channel> userChannelMap = Maps.newHashMap();

}
