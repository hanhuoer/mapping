package club.scoder.app.mapping.client.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientContext implements InitializingBean {

    private static final String RUNTIME_DIR = System.getProperty("user.dir");

    private final ClientConfiguration CONFIGURATION;

    private SSLContext sslContext;


    public ClientConfiguration getConfiguration() {
        return CONFIGURATION;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initSSLContext();
    }

    private void initSSLContext() {
        if (CONFIGURATION.getSslEnable()) {
            log.info("ssl is enable.");
            String sslJksPath = CONFIGURATION.getSslJksPath();
            log.info("initializing ssl context, jks path: {}", sslJksPath);
            if (!StringUtils.hasLength(sslJksPath)) {
                log.warn("the jks path is null or empty.");
                return;
            }
            String sslJksFilePath = RUNTIME_DIR + File.separator + sslJksPath;
            log.info("jks file path: {}", sslJksFilePath);

            String sslKeyStorePassword = CONFIGURATION.getSslKeyStorePassword();
            if (!StringUtils.hasText(sslKeyStorePassword)) {
                log.info("key store password can not be null.");
                return;
            }

            try {
                FileInputStream fileInputStream = new FileInputStream(sslJksFilePath);
                SSLContext clientSSLContext = SSLContext.getInstance("TLSv1");
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(fileInputStream, sslKeyStorePassword.toCharArray());

                // auth
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

                clientSSLContext.init(null, trustManagers, null);
                sslContext = clientSSLContext;
                log.info("ssl initialized successfully.");
            } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | KeyManagementException e) {
                log.error("initialization failed; cause: {}, message: {}", e.getCause(), e.getMessage());
            }
        } else {
            log.info("ssl is not enable.");
        }
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

}