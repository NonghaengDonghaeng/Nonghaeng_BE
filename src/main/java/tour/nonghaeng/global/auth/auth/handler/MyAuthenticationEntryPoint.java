package tour.nonghaeng.global.auth.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tour.nonghaeng.domain.test.dto.JwtValidDto;

import java.io.IOException;

@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        JwtValidDto responseDto = JwtValidDto.builder()
                .valid(false)
                .message("인증 실패!")
                .build();
        String result = objectMapper.writeValueAsString(responseDto);
        response.getWriter().write(result);

        log.error("인증 예외 발생", authException);
        log.info("인증에 실패했습니다. 메시지 : {}", authException.getMessage());
    }
}