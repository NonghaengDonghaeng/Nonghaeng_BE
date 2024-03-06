package tour.nonghaeng.global.jwt.filter;

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
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.jwt.service.JwtService;
import tour.nonghaeng.global.jwt.util.PasswordUtil;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_USER_LOGIN_URL = "/login";
    private static final String NO_CHECK_SELLER_LOGIN_URL = "/seller-login";

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 로그인 요청이면 바로 다음필터 호출
        if(request.getRequestURI().equals(NO_CHECK_USER_LOGIN_URL) ||
                request.getRequestURI().equals(NO_CHECK_SELLER_LOGIN_URL)){

            filterChain.doFilter(request, response);
            return;
        }

        //AccessToken 만료되어 refresh 요청한 경우 제외하고 모두 null
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        //RefreshToken 존재시 RefreshToken 검증 후 AccessToken 재발급
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response,refreshToken);
            return;
        }

        // AccessToken 검증 후 인증처리
        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request,response,filterChain);
        }

    }

    //RefreshToken 검증 후 AccessToken & RefreshToken 재발급
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {

        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    String reIssueRefreshToken = reIssueRefreshToken(user);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getNumber(),"user"),
                            reIssueRefreshToken );
                });

    }

    //RefreshToken 재발급 후 DB 업데이트
    private String reIssueRefreshToken(User user){

        String reIssueRefreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(reIssueRefreshToken);
        userRepository.saveAndFlush(user);

        return reIssueRefreshToken;
    }

    //AccessToken 확인 후 Claim 전화번호를 통해 유저객체 뽑아내 인증처리 후 다음 인증필터로 진행
    public void checkAccessTokenAndAuthentication(HttpServletRequest request,HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException,IOException {

        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken->jwtService.extractType(accessToken)
                        .ifPresent(type->{
                            log.info("인증객체의 type:{}", type);
                            if (type.equals("user")) {
                                jwtService.extractNumber(accessToken)
                                        .ifPresent(number -> userRepository.findByNumber(number)
                                                .ifPresent(this::saveUserAuthentication));
                            } else if (type.equals("seller")) {
                                jwtService.extractNumber(accessToken)
                                        .ifPresent(username -> sellerRepository.findByUsername(username)
                                                .ifPresent(this::saveSellerAuthentication));
                            }
                        })
                );
        log.info("JwtAuthenticationFilter: JWT필터 종료.");
        filterChain.doFilter(request, response);

    }

    //User 통해 UserDetails 생성하여 인증처리
    public void saveUserAuthentication(User user){

        String password = user.getPassword();

        //소셜로그인 경우 비밀번호 없으니 임의로 설정하여 인증
        if (password == null) {
            //TODO: 소셜 로그인시 socialId값으로 비밀번호 설정 방안 생각하기
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getNumber())
                .password(password)
                .roles(user.getRole().name())
                .build();

        //Authentication 인증객체 생성
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        //인증객체 인증 허가 처리
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    //User 통해 UserDetails 생성하여 인증처리
    public void saveSellerAuthentication(Seller seller){

        String password = seller.getPassword();

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(seller.getUsername())
                .password(password)
                .roles(seller.getRole().name())
                .build();

        //Authentication 인증객체 생성
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        //인증객체 인증 허가 처리
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
