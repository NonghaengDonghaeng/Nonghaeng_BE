package tour.nonghaeng.global.auth.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.repo.MemberRepository;
import tour.nonghaeng.global.auth.jwt.service.JwtService;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        String username = extractUsername(authentication);

        String accessToken = jwtService.createAccessToken(username,extractRole(authentication));
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response,accessToken,refreshToken);

        memberRepository.findByUsername(username)
                .ifPresent(member -> {
                    member.updateRefreshToken(refreshToken);
                    memberRepository.saveAndFlush(member);
                });

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("소비자 로그인 성공");

        log.info("로그인에 성공하였습니다. username : {}", username);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("로그인에 성공하였습니다. 만료 기간 : {}", accessTokenExpiration);



    }

    private String extractUsername(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return  userDetails.getUsername();
    }

    private Role extractRole(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        //여기서에는 ROLE_ 이 붙은채로 나옴
        String memberRole = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        return Role.findByKey(memberRole);
    }
}
