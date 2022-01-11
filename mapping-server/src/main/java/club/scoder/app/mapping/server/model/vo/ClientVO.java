package club.scoder.app.mapping.server.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ClientVO {

    private String id;
    private String name;
    private List<ProxyVO> proxyList;

}
