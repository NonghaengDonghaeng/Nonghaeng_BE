package tour.nonghaeng.domain.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.test.dto.JwtValidDto;
import tour.nonghaeng.domain.test.service.DummyDataService;
import tour.nonghaeng.global.auth.auth.service.AuthService;
import tour.nonghaeng.global.exception.GlobalException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final AuthService authService;
    private final DummyDataService dummyDataService;

    @PostMapping("/test/exception")
    public ResponseEntity<String> exceptionTest(@RequestParam String number) {

        if (number.equals("1")) {
            throw GlobalException.EXCEPTION;
        }

        return new ResponseEntity<>("exception test 성공", HttpStatus.OK);
    }

    @GetMapping("/valid")
    public ResponseEntity<JwtValidDto> jwtValidateApi(Authentication authentication) {

        Role role = authService.findRole(authentication);
        JwtValidDto response = JwtValidDto.builder()
                .valid(true)
                .role(role)
                .message("jwt 유효합니다.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test/jwt")
    public ResponseEntity<String> jwtTest(Authentication authentication) {

        Role role = authService.findRole(authentication);

        return new ResponseEntity<>("jwt 검증, 권한: " + role.getKey(), HttpStatus.OK);
    }

    @GetMapping("/test/user-role")
    public ResponseEntity<String> userRoleTest(Authentication authentication) {

        Member member = authService.toMemberEntity(authentication);
        String name = member.getName();

        return new ResponseEntity<>("user authorization test 성공, user 이름: " + name, HttpStatus.OK);
    }

    @GetMapping("/test/seller-role")
    public ResponseEntity<String> sellerRoleTest(Authentication authentication) {

        Member member = authService.toMemberEntity(authentication);

        String name = member.getName();

        return new ResponseEntity<>("seller authorization test 성공, seller 이름: " + name, HttpStatus.OK);

    }


    @GetMapping("/test/set-data")
    public ResponseEntity<String> setDataTest() {
        dummyDataService.setDummyData();
        return new ResponseEntity<>("set dummy data", HttpStatus.OK);
    }
}
