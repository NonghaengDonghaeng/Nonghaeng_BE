package tour.nonghaeng.domain.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.like.service.LikeService;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final AuthService authService;
    private final LikeService likeService;

    @GetMapping("/{type}/{id}")
    public ResponseEntity<String> clickLikes(Authentication authentication,
                                             @PathVariable("type")String type,
                                             @PathVariable("id")Long id) {
        User user = authService.toUserEntity(authentication);

        if (likeService.clickLike(user, id, type)) {
            return new ResponseEntity<>(type+" 좋아요!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(type+" 좋아요 해제!", HttpStatus.OK);
    }

}
