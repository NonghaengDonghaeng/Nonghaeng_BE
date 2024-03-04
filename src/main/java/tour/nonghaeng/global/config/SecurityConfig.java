package tour.nonghaeng.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.jwt.filter.JwtAuthenticationFilter;
import tour.nonghaeng.global.jwt.service.JwtService;
import tour.nonghaeng.global.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import tour.nonghaeng.global.login.handler.UserLoginFailureHandler;
import tour.nonghaeng.global.login.handler.UserLoginSuccessHandler;
import tour.nonghaeng.global.login.service.UserLoginService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserLoginService userLoginService;
    private final UserRepository userRepository;

    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(seesion -> seesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/join").permitAll()
                        .anyRequest().authenticated());

        //logout 필터 -> jwt 필터 -> customUserLogin 필터
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
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
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {

        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);

        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(userAuthenticationManager());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(userLoginSuccessHandler());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(userLoginFailureHandler());

        return customJsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userRepository);

        return jwtAuthenticationFilter;
    }
}
