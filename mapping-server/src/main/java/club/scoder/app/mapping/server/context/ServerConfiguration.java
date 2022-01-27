package club.scoder.app.mapping.server.context;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@ConfigurationProperties(prefix = "mapping")
@Data
@Slf4j
public class ServerConfiguration implements Serializable, InitializingBean {

    private static final long serialVersionUID = 4714080073576164473L;

    private static final String DEFAULT_SERVER_HOST = "127.0.0.1";
    private static final int DEFAULT_SERVER_PORT = 18001;
    private static final int DEFAULT_WEB_PORT = 18000;

    private String serverName;
    private String version;
    private String serverHost;
    private Integer serverPort;
    private Integer webPort;
    private Boolean sslEnable = false;
    private String sslJksPath;
    private String sslKeyStorePassword;
    private String sslKeyManagerPassword;
    private Boolean sslNeedsClientAuth = false;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (serverHost == null) {
            serverHost = DEFAULT_SERVER_HOST;
            log.info("use the default server host: {}.", DEFAULT_SERVER_HOST);
        }
        if (serverPort == null) {
            serverPort = DEFAULT_SERVER_PORT;
            log.info("use the default server port: {}.", DEFAULT_SERVER_PORT);
        }
        if (webPort == null) {
            webPort = DEFAULT_WEB_PORT;
            log.info("use the default web port: {}.", DEFAULT_WEB_PORT);
        }
    }

}