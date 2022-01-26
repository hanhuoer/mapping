package club.scoder.app.mapping.server.auth.jwt;

import club.scoder.app.mapping.common.http.Response;
import club.scoder.app.mapping.common.http.code.ErrorCode;
import club.scoder.app.mapping.common.http.code.FailureCode;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            String token = jwtTokenProvider.getToken(request);
            if (token != null && jwtTokenProvider.validate(token)) {
                String username = jwtTokenProvider.getUsername(token);
                Authentication auth = jwtTokenProvider.getAuthentication(username);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (JwtInvalidAuthenticationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.error(
                    FailureCode.JWT_INVALID_TOKEN, "Invalid token.", "")));
            response.getWriter().flush();
            return;
        } catch (JwtExpiredAuthenticationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.error(
                    FailureCode.JWT_EXPIRED_TOKEN, "Expired token.", "")));
            response.getWriter().flush();
            return;
        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.error(
                    FailureCode.JWT_WRONG_TOKEN, "Wrong token.", "")));
            response.getWriter().flush();
            return;
        } catch (JwtAllowRefreshException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.error(
                    FailureCode.JWT_NOT_ALLOWED_REFRESH_TOKEN, "Not Allowed refresh.", "")));
            response.getWriter().flush();
            return;
        } catch (AuthenticationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.error(
                    FailureCode.JWT_UNAUTHORIZED_TOKEN, "Unauthorized token.", "")));
            response.getWriter().flush();
            return;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(Response.error(
                    ErrorCode.CODE, "Internal server error.", "")));
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
