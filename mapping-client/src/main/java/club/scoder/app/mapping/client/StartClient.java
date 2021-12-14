package club.scoder.app.mapping.client;

import club.scoder.app.mapping.client.context.ClientContext;
import club.scoder.app.mapping.client.proxy.MappingClient;

public class StartClient {

    public static void main(String[] args) {
        ClientContext clientContext = ClientContext.instance();
        MappingClient mappingClient = new MappingClient(clientContext);
        mappingClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(mappingClient::stop));
    }

}
