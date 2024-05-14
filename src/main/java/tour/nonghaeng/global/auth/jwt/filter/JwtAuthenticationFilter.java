package tour.nonghaeng.global.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.repo.MemberRepository;
import tour.nonghaeng.global.auth.jwt.service.JwtService;
import tour.nonghaeng.global.auth.jwt.util.PasswordUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_USER_LOGIN_URL = "/login";
    private static final String NO_CHECK_SELLER_LOGIN_URL = "/seller-login";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 로그인 요청이면 바로 다음필터 호출
        if(request.getRequestURI().equals(NO_CHECK_USER_LOGIN_URL) ||
                request.getRequestURI().equals(NO_CHECK_SELLER_LOGIN_URL)){

            filterChain.doFilter(request, response);
            return;
        }

        //AccessToken 만료되어 refresh 요청한 경우 제외하고 모두 null

        log.info("//AccessToken 만료되어 refresh 요청한 경우 제외하고 모두 null");
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        //RefreshToken 존재시 RefreshToken 검증 후 AccessToken 재발급
        if (refreshToken != null) {
            log.info("//RefreshToken 존재시 RefreshToken 검증 후 AccessToken 재발급");
            checkRefreshTokenAndReIssueAccessToken(response,refreshToken);
            return;
        }

        // AccessToken 검증 후 인증처리
        log.info("// AccessToken 검증 후 인증처리");
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    //RefreshToken 검증 후 AccessToken & RefreshToken 재발급
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {

        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member ->
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getUsername(), member.getRole()),
                            reIssueRefreshToken(member))
                );


    }
    //RefreshToken 재발급 후 DB 업데이트
    private String reIssueRefreshToken(Member member){

        String reIssueRefreshToken = jwtService.createRefreshToken();
        member.updateRefreshToken(reIssueRefreshToken);
        memberRepository.saveAndFlush(member);

        return reIssueRefreshToken;
    }


    //AccessToken 확인 후 Claim 전화번호를 통해 유저객체 뽑아내 인증처리 후 다음 인증필터로 진행
    public void checkAccessTokenAndAuthentication(HttpServletRequest request,HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException,IOException {

        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .flatMap(jwtService::extractUsername)
                .flatMap(memberRepository::findByUsername)
                .ifPresent(this::saveAuthentication);


        log.info("JwtAuthenticationFilter: JWT필터 종료.");
        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(Member member) {

        String password = member.getPassword();

        if(password == null){
            password = PasswordUtil.generateRandomPassword();
        }

        //안에 들어가서 확인해보면 roles에서 자동으로 ROLE_이 붙여지기 때문에 member.getRole.getKet()가 아닌 .name()으로
        // 하지만 Authentication 에서 role 조회할땐 ROLE_ 붙은채로 나옴
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(member.getUsername())
                .password(password)
                .roles(member.getRole().name())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }




}
