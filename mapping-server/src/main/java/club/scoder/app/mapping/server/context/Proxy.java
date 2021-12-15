package club.scoder.app.mapping.server.context;

import lombok.Data;

/**
 * store the proxy mapping relationship of client
 */
@Data
public class Proxy {

    private String name;

    /**
     * proxy port
     */
    private Integer serverPort;
    /**
     * real server host
     */
    private String clientHost;
    /**
     * real server port
     */
    private Integer clientPort;
}