package tour.nonghaeng.global.auth.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import tour.nonghaeng.domain.test.dto.JwtValidDto;

import java.io.IOException;

@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        JwtValidDto responseDto = JwtValidDto.builder()
                .valid(true)
                .message("접근 실패!")
                .build();
        String result = objectMapper.writeValueAsString(responseDto);
        response.getWriter().write(result);

        log.info("접근 권한이 없습니다. 메시지 : {}", exception.getMessage());
    }

}

