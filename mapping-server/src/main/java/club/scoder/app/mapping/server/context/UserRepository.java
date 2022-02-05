package club.scoder.app.mapping.server.context;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private static final Map<String, User> USER_MAP = Maps.newHashMap();
    private final ServerConfiguration serverConfiguration;


    @PostConstruct
    public void init() {
        String username = serverConfiguration.getWebUsername();
        String password = serverConfiguration.getWebPassword();
        USER_MAP.put(username, User.builder()
                .username(username)
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
                .build());
    }

    public User getByUsername(String username) {
        return USER_MAP.get(username);
    }

    public Optional<User> getOptionalByUsername(String username) {
        return Optional.ofNullable(USER_MAP.get(username));
    }

}
