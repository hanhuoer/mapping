package club.scoder.app.mapping.server.service;

import club.scoder.app.mapping.server.context.ChannelManager;
import club.scoder.app.mapping.server.context.ServerConfiguration;
import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.model.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final ServerContext serverContext;
    private final ServerConfiguration configuration;


    public OverviewVO getOverview() {
        OverviewVO overviewVO = new OverviewVO();
        overviewVO.setVersion(configuration.getVersion());
        overviewVO.setBindPort(configuration.getServerPort());
        overviewVO.setWebPort(configuration.getWebPort());
        overviewVO.setClientTotal(serverContext.getClientList().size());
        overviewVO.setClientOnline(ChannelManager.clientIdChannelMap.size());
        overviewVO.setProxyTotal(serverContext.getProxyPortList().size());
        overviewVO.setProxyActive(ChannelManager.proxyPortUserChannelIdMap.size());
        return overviewVO;
    }

}
