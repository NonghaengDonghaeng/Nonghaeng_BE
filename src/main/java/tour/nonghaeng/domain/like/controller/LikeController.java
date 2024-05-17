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
import tour.nonghaeng.domain.etc.enums.like.LikeType;
import tour.nonghaeng.domain.like.exception.LikeException;
import tour.nonghaeng.domain.like.service.registry.LikeServiceRegistry;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final AuthService authService;

    private final LikeServiceRegistry likeServiceRegistry;



    @GetMapping("/{type}/{id}")
    public ResponseEntity<String> clickLikes(Authentication authentication,
                                             @PathVariable("type")String type,
                                             @PathVariable("id")Long id) {
        Member user = authService.toMemberEntity(authentication);

        boolean onLike = likeServiceRegistry.getService(LikeType.ofDtype(type))
                .map(service -> service.clickLike(user, id)).
                orElseThrow(LikeException::new);

        if (onLike) {
            return new ResponseEntity<>(type+" 좋아요!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(type+" 좋아요 해제!", HttpStatus.OK);
    }

}
