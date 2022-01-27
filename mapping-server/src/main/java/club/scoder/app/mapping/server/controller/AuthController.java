package club.scoder.app.mapping.server.controller;

import club.scoder.app.mapping.common.http.Response;
import club.scoder.app.mapping.server.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("auth")
@RequestMapping("/auth/")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


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
        return Response.success(refresh);
    }

}
