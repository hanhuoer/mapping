package club.scoder.app.mapping.server.proxy;

import club.scoder.app.mapping.server.context.ServerContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class StartServer implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerContext serverContext = ServerContext.instance();
        MappingServer mappingServer = new MappingServer(serverContext);
        mappingServer.start();
        UserServer userServer = new UserServer(serverContext);
        userServer.start();
    }

}
