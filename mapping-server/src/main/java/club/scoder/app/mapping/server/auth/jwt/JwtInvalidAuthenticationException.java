package club.scoder.app.mapping.server.auth.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtInvalidAuthenticationException extends AuthenticationException {

    public JwtInvalidAuthenticationException(String e) {
        super(e);
    }

}