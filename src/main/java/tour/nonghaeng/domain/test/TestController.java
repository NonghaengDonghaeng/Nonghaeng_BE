package tour.nonghaeng.domain.test;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.exception.GlobalException;

@RestController("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final AuthService authService;

    @PostMapping("/test-exception")
    public ResponseEntity<String> exceptionTest(@RequestParam String number) {

        if (number.equals("1")) {
            throw GlobalException.EXCEPTION;
        }

        return new ResponseEntity<>("exception test 성공", HttpStatus.OK);
    }

    @GetMapping("/test-jwt")
    public ResponseEntity<String> jwtTest(Authentication authentication) {
        return new ResponseEntity<>("jwt test 성공", HttpStatus.OK);
    }

    @GetMapping("/test-user-role")
    public ResponseEntity<String> userRoleTest(Authentication authentication) {

        User user = authService.toUserEntity(authentication);
        String name = user.getName();

        return new ResponseEntity<>("user authorization test 성공, user 이름: "+name, HttpStatus.OK);
    }

    @GetMapping("/test-seller-role")
    public ResponseEntity<String> sellerRoleTest(Authentication authentication) {
        Seller seller = authService.toSellerEntity(authentication);
        String name = seller.getName();

        return new ResponseEntity<>("seller authorization test 성공, seller 이름: "+name, HttpStatus.OK);

    }

}
