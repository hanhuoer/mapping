package club.scoder.app.mapping.server.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerConfiguration implements Serializable {

    private static final long serialVersionUID = 4714080073576164473L;

    private static volatile ServerConfiguration INSTANCE = null;

    private String serverHost;
    private Integer serverPort;

    private ServerConfiguration() {
    }

    protected static ServerConfiguration instance() {
        if (INSTANCE == null) {
            synchronized (ServerConfiguration.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServerConfiguration();
                }
            }
        }
        return INSTANCE;
    }
}