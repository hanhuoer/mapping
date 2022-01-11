package club.scoder.app.mapping.server.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProxyVO {

    private String name;
    private Integer serverPort;
    private String clientHost;
    private Integer clientPort;
    private List<AllowVO> allows;

}