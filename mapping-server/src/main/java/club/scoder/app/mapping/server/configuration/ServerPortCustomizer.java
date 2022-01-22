package club.scoder.app.mapping.server.configuration;

import club.scoder.app.mapping.server.context.ServerConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private final ServerConfiguration configuration;


    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(configuration.getWebPort());
    }

}