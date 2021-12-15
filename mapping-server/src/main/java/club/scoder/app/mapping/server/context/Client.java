package club.scoder.app.mapping.server.context;

import lombok.Data;

import java.util.List;

/**
 * client proxy info.
 */
@Data
public class Client {

    /**
     * client id
     */
    private String id;
    /**
     * client alias
     */
    private String name;
    /**
     * proxy list
     */
    private List<Proxy> proxyList;

}