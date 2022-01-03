package club.scoder.app.mapping.client.context;

import io.netty.util.internal.StringUtil;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mapping")
@Data
public class ClientConfiguration implements InitializingBean {

    private static volatile ClientConfiguration INSTANCE = null;

    private String clientId;
    private String serverHost;
    private Integer serverPort;
    private Boolean sslEnable = false;
    private String sslJksPath;
    private String sslKeyStorePassword;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtil.isNullOrEmpty(clientId)) {
            throw new RuntimeException("client id can not be null, please check conf.");
        }
        if (StringUtil.isNullOrEmpty(serverHost)) {
            throw new RuntimeException("server host can not be null, please check conf.");
        }
        if (serverPort == null) {
            throw new RuntimeException("server port can not be null, please check conf.");
        }
    }

}