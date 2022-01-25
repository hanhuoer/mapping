package club.scoder.app.mapping.server.auth.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAllowRefreshException extends AuthenticationException {

    public JwtAllowRefreshException(String e) {
        super(e);
    }

}