package club.scoder.app.mapping.server.proxy;

import club.scoder.app.mapping.server.context.ServerContext;
import club.scoder.app.mapping.server.filter.ClientHttpRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartServer implements InitializingBean {

    private final ServerContext serverContext;
    private final ClientHttpRequestFilter clientHttpRequestFilter;


    @Override
    public void afterPropertiesSet() {
        MappingServer mappingServer = new MappingServer(serverContext);
        mappingServer.start();
        UserServer userServer = new UserServer(serverContext, clientHttpRequestFilter);
        userServer.start();
        serverContext.addServer(userServer);
    }

}
