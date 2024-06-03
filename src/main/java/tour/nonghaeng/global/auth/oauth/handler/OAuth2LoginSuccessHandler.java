package tour.nonghaeng.global.auth.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.data.repo.MemberRepository;
import tour.nonghaeng.domain.member.data.repo.UserRepository;
import tour.nonghaeng.global.auth.jwt.service.JwtService;
import tour.nonghaeng.global.auth.oauth.CustomOAuth2User;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            if (oAuth2User.getRole() == Role.GUEST_USER) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getUsername(), Role.USER);
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//                response.sendRedirect("oauth2/sign-up");    //프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);

                User findUser = userRepository.findByUsername(oAuth2User.getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("전화번호에 해당하는 유저가 없습니다."));
                findUser.authorizeUser();
            } else {
                loginSuccess(response, oAuth2User);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    //TODO: 소셜 로그인 시에도 JWT인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리하는거 추가하기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getUsername(),Role.USER);
        String refreshToken = jwtService.createRefreshToken();

        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getUsername(), refreshToken);


        String redirectUrl = "https://nonghaeng.site/acount/login?accessToken="+accessToken;

//        response.addHeader(HttpHeaders.SET_COOKIE,createAuthCookie("Authorization",accessToken).toString());
        response.addCookie(createCookie("Authorization",accessToken));
        response.sendRedirect(redirectUrl);

    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
