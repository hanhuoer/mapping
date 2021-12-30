package club.scoder.app.mapping.server.proxy;

import club.scoder.app.mapping.server.context.ServerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartServer implements InitializingBean {

    private final ServerContext serverContext;


    @Override
    public void afterPropertiesSet() {
        MappingServer mappingServer = new MappingServer(serverContext);
        mappingServer.start();
        UserServer userServer = new UserServer(serverContext);
        userServer.start();
    }

}
