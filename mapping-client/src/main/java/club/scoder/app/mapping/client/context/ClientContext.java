package club.scoder.app.mapping.client.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientContext {

    private static final String RUNTIME_DIR = System.getProperty("user.dir");

    private final ClientConfiguration CONFIGURATION;


    public ClientConfiguration getConfiguration() {
        return CONFIGURATION;
    }

}