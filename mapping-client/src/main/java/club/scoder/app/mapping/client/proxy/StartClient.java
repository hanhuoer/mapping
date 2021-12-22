package club.scoder.app.mapping.client.proxy;

import club.scoder.app.mapping.client.context.ClientContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class StartClient implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientContext clientContext = ClientContext.instance();
        MappingClient mappingClient = new MappingClient(clientContext);
        mappingClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(mappingClient::stop));
    }

}
