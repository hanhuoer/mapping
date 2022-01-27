package club.scoder.app.mapping.server.auth.jwt;

import club.scoder.app.mapping.server.context.ServerConfiguration;
import club.scoder.app.mapping.server.service.UserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final UserDetailService userDetailService;
    private final ServerConfiguration configuration;

    private String secretKey;
    private Long expireSeconds;
    private Long refreshLessSeconds;


    @PostConstruct
    protected void init() {
        ServerConfiguration.Jwt jwt = configuration.getJwt();
        secretKey = Base64.getEncoder().encodeToString(jwt.getSecretKey().getBytes());
        expireSeconds = jwt.getExpireSeconds();
        refreshLessSeconds = jwt.getRefreshLeastSeconds();
    }

    public String generate(String username) {
        Date now = new Date();
        long expireMillis = expireSeconds * 1000;
        Date expireDate = new Date(now.getTime() + expireMillis);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String refresh(String token) {
        long expireSeconds;
        String username;
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Date expiration = claimsJws.getBody().getExpiration();
            expireSeconds = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            username = claimsJws.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredAuthenticationException("Expired token.");
        } catch (Exception e) {
            throw new JwtInvalidAuthenticationException("Wrong token.");
        }
        if (expireSeconds <= 0) {
            log.warn("expired token, cancel refresh.");
            throw new JwtExpiredAuthenticationException("Expired token.");
        } else if (expireSeconds < refreshLessSeconds) {
            return generate(username);
        } else {
            throw new JwtAllowRefreshException("Not allowed refresh.");
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization" +
                "");
        return getToken(authorization);
    }

    public String getToken(String authorization) {
        if (StringUtils.hasText(authorization)) {
            if (authorization.startsWith("Bearer ")) {
                String token = authorization.substring(7);
                log.debug("token: {}", token);
                return token;
            } else {
                log.warn("token format error; token: {}", authorization);
            }
        } else {
            log.debug("token missing.");
        }
        return null;
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("jwt expired; message: {}", e.getMessage());
            throw new JwtExpiredAuthenticationException("expired.");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error("jwt exception; message: {}", e.getMessage(), e);
            throw new JwtInvalidAuthenticationException("verification failed.");
        } catch (JwtException e) {
            log.error("jwt exception; message: {}", e.getMessage(), e);
            throw new JwtInvalidAuthenticationException("invalid JWT token.");
        }
    }

    public Authentication getAuthentication(String username) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
