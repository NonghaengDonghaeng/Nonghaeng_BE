package tour.nonghaeng.global.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.global.jwt.service.JwtService;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class SellerLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final SellerRepository sellerRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(username, "seller");
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        sellerRepository.findByUsername(username)
                .ifPresent((seller)->{
                    seller.updateRefreshToken(refreshToken);
                    sellerRepository.saveAndFlush(seller);
                });

        log.info("로그인에 성공하였습니다. id : {}", username);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);

    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
