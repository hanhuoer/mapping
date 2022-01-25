package club.scoder.app.mapping.server.context;

import com.google.common.collect.Maps;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final Map<String, User> USER_MAP = Maps.newHashMap();


    @PostConstruct
    public void init() {
        USER_MAP.put("admin1", User.builder()
                .username("admin1")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"))
                .build());
    }

    public User getByUsername(String username) {
        return USER_MAP.get(username);
    }

    public Optional<User> getOptionalByUsername(String username) {
        return Optional.ofNullable(USER_MAP.get(username));
    }

}
