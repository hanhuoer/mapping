package club.scoder.app.mapping.client.context;

import lombok.Data;

@Data
public class ClientConfiguration {

    private static volatile ClientConfiguration INSTANCE = null;

    private String clientId;
    private String serverHost;
    private Integer serverPort;


    private ClientConfiguration() {
    }

    protected static ClientConfiguration instance() {
        if (INSTANCE == null) {
            synchronized (ClientConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClientConfiguration();
                }
            }
        }
        return INSTANCE;
    }

}