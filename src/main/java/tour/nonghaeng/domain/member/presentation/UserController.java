package tour.nonghaeng.domain.member.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.dto.mypage.MyPageUserDto;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.service.MyPageService;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.global.auth.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final MyPageService myPageService;
    private final AuthService authService;


    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinDto userJoinDto){

        log.info(userJoinDto.toString());
        userService.join(userJoinDto);


        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }

    @GetMapping("/my-page")
    public ResponseEntity<MyPageUserDto> getMyPage(Authentication authentication){

        Member user = authService.toMemberEntity(authentication);

        MyPageUserDto userMyPage = myPageService.getUserMyPage(user);

        return new ResponseEntity<>(userMyPage, HttpStatus.OK);
    }

}
