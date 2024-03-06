package tour.nonghaeng.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.jwt.filter.JwtAuthenticationFilter;
import tour.nonghaeng.global.jwt.service.JwtService;
import tour.nonghaeng.global.login.filter.CustomJsonSellerAuthenticationFilter;
import tour.nonghaeng.global.login.filter.CustomJsonUserAuthenticationFilter;
import tour.nonghaeng.global.login.handler.SellerLoginFailureHandler;
import tour.nonghaeng.global.login.handler.SellerLoginSuccessHandler;
import tour.nonghaeng.global.login.handler.UserLoginFailureHandler;
import tour.nonghaeng.global.login.handler.UserLoginSuccessHandler;
import tour.nonghaeng.global.login.service.SellerLoginService;
import tour.nonghaeng.global.login.service.UserLoginService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserLoginService userLoginService;
    private final UserRepository userRepository;
    private final SellerLoginService sellerLoginService;
    private final SellerRepository sellerRepository;

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(seesion -> seesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/join").permitAll()
                        .requestMatchers("/seller-join").permitAll()
                        .anyRequest().authenticated());

        //logout 필터 -> jwt 필터 -> customUserLogin 필터
        http.addFilterAfter(jwtAuthenticationFilter(), LogoutFilter.class);
        http.addFilterAfter(customJsonUserAuthenticationFilter(), JwtAuthenticationFilter.class);
        http.addFilterAfter(customJsonSellerAuthenticationFilter(), CustomJsonUserAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(name="userAuthenticationManager")
    @Primary
    public AuthenticationManager userAuthenticationManager() {

        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
        userProvider.setPasswordEncoder(passwordEncoder());
        userProvider.setUserDetailsService(userLoginService);

        return new ProviderManager(userProvider);
    }

    @Bean
    public UserLoginSuccessHandler userLoginSuccessHandler() {
        return new UserLoginSuccessHandler(jwtService, userRepository);
    }

    @Bean
    public UserLoginFailureHandler userLoginFailureHandler() {
        return new UserLoginFailureHandler();
    }

    @Bean
    public CustomJsonUserAuthenticationFilter customJsonUserAuthenticationFilter() {

        CustomJsonUserAuthenticationFilter customJsonUserAuthenticationFilter
                = new CustomJsonUserAuthenticationFilter(objectMapper);

        customJsonUserAuthenticationFilter.setAuthenticationManager(userAuthenticationManager());
        customJsonUserAuthenticationFilter.setAuthenticationSuccessHandler(userLoginSuccessHandler());
        customJsonUserAuthenticationFilter.setAuthenticationFailureHandler(userLoginFailureHandler());

        return customJsonUserAuthenticationFilter;
    }

    @Bean(name="sellerAuthenticationManger")
    public AuthenticationManager sellerAuthenticationManger() {

        DaoAuthenticationProvider sellerProvider = new DaoAuthenticationProvider();
        sellerProvider.setPasswordEncoder(passwordEncoder());
        sellerProvider.setUserDetailsService(sellerLoginService);

        return new ProviderManager(sellerProvider);
    }

    @Bean
    public SellerLoginSuccessHandler sellerLoginSuccessHandler() {
        return new SellerLoginSuccessHandler(jwtService, sellerRepository);
    }

    @Bean
    public SellerLoginFailureHandler sellerLoginFailureHandler() {
        return new SellerLoginFailureHandler();
    }

    @Bean
    public CustomJsonSellerAuthenticationFilter customJsonSellerAuthenticationFilter() {
        CustomJsonSellerAuthenticationFilter customJsonSellerAuthenticationFilter
                = new CustomJsonSellerAuthenticationFilter(objectMapper);

        customJsonSellerAuthenticationFilter.setAuthenticationManager(sellerAuthenticationManger());
        customJsonSellerAuthenticationFilter.setAuthenticationSuccessHandler(sellerLoginSuccessHandler());
        customJsonSellerAuthenticationFilter.setAuthenticationFailureHandler(sellerLoginFailureHandler());

        return customJsonSellerAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userRepository,sellerRepository);

        return jwtAuthenticationFilter;
    }
}
