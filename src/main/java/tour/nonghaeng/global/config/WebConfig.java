package tour.nonghaeng.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://nonghaeng-fe.vercel.app")
//                .allowedOriginPatterns("*")
//                .allowedOrigins("*") // 허용할 출처
                .allowedMethods("GET", "OPTIONS", "POST", "DELETE") // 허용할 HTTP method
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true); // 쿠키 인증 요청 허용
    }
}
