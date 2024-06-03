package tour.nonghaeng.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.repo.MemberRepository;
import tour.nonghaeng.global.auth.handler.MyAccessDeniedHandler;
import tour.nonghaeng.global.auth.handler.MyAuthenticationEntryPoint;
import tour.nonghaeng.global.auth.jwt.filter.JwtAuthenticationFilter;
import tour.nonghaeng.global.auth.jwt.service.JwtService;
import tour.nonghaeng.global.auth.login.filter.CustomJsonAuthenticationFilter;
import tour.nonghaeng.global.auth.login.handler.CustomLoginFailureHandler;
import tour.nonghaeng.global.auth.login.handler.CustomLoginSuccessHandler;
import tour.nonghaeng.global.auth.login.service.LoginService;
import tour.nonghaeng.global.auth.oauth.handler.OAuth2LoginFailureHandler;
import tour.nonghaeng.global.auth.oauth.handler.OAuth2LoginSuccessHandler;
import tour.nonghaeng.global.auth.oauth.service.CustomOAuth2UserService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final LoginService loginService;

    private final MemberRepository memberRepository;

    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.addExposedHeader("Authorization");   //Authorization 추가 코드
//                        config.setAllowedOriginPatterns(Collections.singletonList("*"));
                        config.setAllowedOrigins(Collections.singletonList("https://nonghaeng.site"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);           //서로다른 도메인에서 쿠키같이 민간한 정보도 전송
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                        //same-site, secure 설정 나중에 추가
                        //TODO: allow credentials 할때 와일드카드로 하면 안됨
                    }
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(seesion -> seesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/test/exception").permitAll()
                        .requestMatchers("/test/user-role").hasRole(Role.USER.name())
                        .requestMatchers("/test/seller-role").hasRole(Role.SELLER.name())
                        .requestMatchers("/join").permitAll()
                        .requestMatchers("/seller-join").permitAll()
                        .requestMatchers("/*/seller/**").hasRole(Role.SELLER.name())
                        .requestMatchers("/*/*/seller/**").hasRole(Role.SELLER.name())
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler));

        http.exceptionHandling(exceptionHandlingConfigurer -> {
            exceptionHandlingConfigurer.accessDeniedHandler(myAccessDeniedHandler());
            exceptionHandlingConfigurer.authenticationEntryPoint(myAuthenticationEntryPoint());
        });

        //logout 필터 -> jwt 필터 -> customUserLogin 필터 -> customSellerLogin 필터
        http.addFilterAfter(customJsonAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomJsonAuthenticationFilter.class);
//        http.addFilterAfter(customJsonAuthenticationFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    //인증예외
    @Bean
    public MyAuthenticationEntryPoint myAuthenticationEntryPoint() {
        return new MyAuthenticationEntryPoint();
    }

    //인가예외
    @Bean
    MyAccessDeniedHandler myAccessDeniedHandler(){
        return new MyAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(loginService);

        return new ProviderManager(authProvider);
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    @Bean
    public CustomJsonAuthenticationFilter customJsonAuthenticationFilter() {
        CustomJsonAuthenticationFilter customJsonAuthenticationFilter = new CustomJsonAuthenticationFilter(objectMapper);

        customJsonAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customJsonAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customJsonAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());

        return customJsonAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {

        return new JwtAuthenticationFilter(jwtService, memberRepository);
    }
}
