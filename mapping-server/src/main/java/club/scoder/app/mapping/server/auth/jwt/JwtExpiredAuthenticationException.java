package club.scoder.app.mapping.server.auth.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredAuthenticationException extends AuthenticationException {

    public JwtExpiredAuthenticationException(String e) {
        super(e);
    }

}