package club.scoder.app.mapping.server.controller;

import club.scoder.app.mapping.common.http.Response;
import club.scoder.app.mapping.server.auth.jwt.JwtTokenProvider;
import club.scoder.app.mapping.server.context.ServerConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController("auth")
@RequestMapping("/auth/")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ServerConfiguration configuration;


    @PostMapping("sign/in")
    public Response<Object> signIn(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtTokenProvider.generate(username);
        return Response.success("Bearer " + token);
    }

    @PostMapping("refresh")
    public Response<Object> refresh(@RequestHeader("Authorization") String authorization) {
        String token = jwtTokenProvider.getToken(authorization);
        String refresh = jwtTokenProvider.refresh(token);
        return Response.success("Bearer " + refresh);
    }

    @GetMapping("get/refresh/least/seconds")
    public Response<Long> getRefreshLeastSeconds() {
        Long result = configuration.getJwt().getRefreshLeastSeconds();
        return Response.success(result);
    }

    @GetMapping("get/expire/at")
    public Response<Long> getExpireAt(@RequestHeader("Authorization") String authorization) {
        String token = jwtTokenProvider.getToken(authorization);
        long result = jwtTokenProvider.getExpireAt(token);
        return Response.success(result);
    }

}
