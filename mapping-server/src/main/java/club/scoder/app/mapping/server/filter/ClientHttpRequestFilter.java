package club.scoder.app.mapping.server.filter;

import club.scoder.app.mapping.server.context.Allow;
import club.scoder.app.mapping.server.context.Client;
import club.scoder.app.mapping.server.context.Proxy;
import club.scoder.app.mapping.server.parser.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientHttpRequestFilter {

    private final FilterAdapter filterAdapter;


    /**
     * if client is null, return false.
     * if proxy list of the client is null or empty, return false.
     * if allow rule of the proxy is null or empty, return false.
     * <p>
     * match based on the configuration sequence, return true if matched.
     *
     * @param httpRequest http request.
     * @param port        proxy port.
     * @param client      client.
     * @return true if matched.
     */
    public boolean filter(HttpRequest httpRequest, int port, Client client) {
        if (client == null) return false;
        List<Proxy> proxyList = client.getProxyList();
        if (CollectionUtils.isEmpty(proxyList)) return false;
        for (Proxy proxy : proxyList) {
            if (!proxy.getServerPort().equals(port)) continue;
            List<Allow> allows = proxy.getAllows();
            if (CollectionUtils.isEmpty(allows)) return false;
            for (Allow allow : allows) {
                boolean match = filterAdapter.match(httpRequest.getPath(), allow.getPath());
                if (match) return true;
            }
        }
        return false;
    }
}
