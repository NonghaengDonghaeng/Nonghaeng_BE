package tour.nonghaeng.global.auth.login.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tour.nonghaeng.global.auth.jwt.service.JwtService;

import java.io.IOException;

@Slf4j
public class CustomGuestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_GUEST_LOGIN_REQUEST_URL = "/guest-login";
    private static final String HTTP_METHOD = "GET";
    private static final String USERNAME_KEY = "guestUser";
    private static final String PASSWORD_KEY = "guestUser";

    private final JwtService jwtService;

    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_GUEST_LOGIN_REQUEST_URL, HTTP_METHOD);



    public CustomGuestAuthenticationFilter(JwtService jwtService) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")) {
            String accessToken = authorization.split("Bearer ")[1];
            if (accessToken != null && jwtService.isTokenValid(accessToken)) {

                return null;
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(USERNAME_KEY, PASSWORD_KEY);

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
