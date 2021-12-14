package club.scoder.app.mapping.client.context;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ClientContext {

    private static final String RUNTIME_DIR = System.getProperty("user.dir");
    private static final String CONF_PATH = RUNTIME_DIR + File.separator + "conf" + File.separator + "client.properties";
    private static final Properties PROPERTIES = new Properties();
    private static final ClientConfiguration CONFIGURATION = ClientConfiguration.instance();

    private static volatile ClientContext INSTANCE = null;


    private ClientContext() {
        initProperties();
        initConfiguration();
    }

    /**
     * Get the client context instance.
     *
     * @return ClientContext's instance
     */
    public static ClientContext instance() {
        if (INSTANCE == null) {
            synchronized (ClientContext.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClientContext();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Load client configuration from {@link ClientContext#CONF_PATH} file.
     */
    private void initProperties() {
        FileInputStream confInputStream = null;
        try {
            confInputStream = new FileInputStream(CONF_PATH);
            PROPERTIES.load(confInputStream);
        } catch (FileNotFoundException e) {
            log.error("client conf not found.");
        } catch (IOException e) {
            log.error("client conf load failed. {}", e.getMessage());
        } finally {
            if (confInputStream != null) {
                try {
                    confInputStream.close();
                } catch (IOException e) {
                    log.error("client conf close failed. {}", e.getMessage());
                }
            }
        }
    }

    /**
     * Convert properties to configuration class.
     */
    private void initConfiguration() {
        if (CONFIGURATION == null) throw new RuntimeException();
        String clientId = PROPERTIES.getProperty("client.id");
        String serverHost = PROPERTIES.getProperty("server.host");
        String serverPort = PROPERTIES.getProperty("server.port");
        if (StringUtil.isNullOrEmpty(clientId)) {
            throw new RuntimeException("client id can not be null, please check conf.");
        }
        if (StringUtil.isNullOrEmpty(serverHost)) {
            throw new RuntimeException("server host can not be null, please check conf.");
        }
        if (StringUtil.isNullOrEmpty(serverPort)) {
            throw new RuntimeException("server port can not be null, please check conf.");
        }
        if (!serverPort.matches("[0-9]+")) {
            throw new RuntimeException("server port incorrect, please check conf.");
        }
        CONFIGURATION.setClientId(clientId);
        CONFIGURATION.setServerHost(serverHost);
        CONFIGURATION.setServerPort(Integer.valueOf(serverPort));
    }

    public ClientConfiguration getConfiguration() {
        return CONFIGURATION;
    }

}