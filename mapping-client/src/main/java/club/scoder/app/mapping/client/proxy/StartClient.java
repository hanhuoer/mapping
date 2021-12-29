package club.scoder.app.mapping.client.proxy;

import club.scoder.app.mapping.client.context.ClientContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartClient implements InitializingBean {

    private final ClientContext clientContext;


    @Override
    public void afterPropertiesSet() {
        MappingClient mappingClient = new MappingClient(clientContext);
        mappingClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(mappingClient::stop));
    }

}
