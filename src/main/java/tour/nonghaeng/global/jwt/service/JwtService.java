package tour.nonghaeng.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.member.repo.UserRepository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ROLE_TYPE_CLAIM = "type";
    private static final String NUMBER_CLAIM = "number";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    //AccessToken 생성 메소드
    public String createAccessToken(String number,String type){

        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(ROLE_TYPE_CLAIM, type)
                .withClaim(NUMBER_CLAIM, number)
                .sign(Algorithm.HMAC512(secretKey));

    }

    //RefreshToken 생성 메소드
    public String createRefreshToken(){

        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));

    }

    //AccessToken 헤더에 실어서 전송
    public void sendAccessToken(HttpServletResponse response,String accessToken){

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);

        log.info("재발급된 Access Token : {}", accessToken);

    }

    //AccessToken + RefreshToken 헤더에 실어서 전송
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);

        log.info("Access Token, Refresh Token 헤더 설정 완료");

    }

    //헤더에서 AccessToken 추출
    public Optional<String> extractAccessToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));

    }

    //헤더에서 RefreshToken 추출
    public Optional<String> extractRefreshToken(HttpServletRequest request){

        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));

    }

    //AccessToken 에서 number 추출
    public Optional<String> extractNumber(String accessToken) {

        try{
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(NUMBER_CLAIM)
                    .asString());
        }catch (Exception e){
            log.error("엑세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }

    }

    //AccessToken 에서 role 추출
    public Optional<String> extractType(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(ROLE_TYPE_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("엑세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    //RefreshToken DB에 저장(업데이트)
    public void updateRefreshToken(String number,String refreshToken){
        userRepository.findByNumber(number)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token){
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }



}
