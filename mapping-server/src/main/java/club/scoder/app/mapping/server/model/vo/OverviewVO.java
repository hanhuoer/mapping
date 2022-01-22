package club.scoder.app.mapping.server.model.vo;

import lombok.Data;

@Data
public class OverviewVO {

    private String version;
    private int bindPort;
    private int webPort;

    private int clientTotal;
    private int clientOnline;

    private int proxyTotal;
    private int proxyActive;

}
