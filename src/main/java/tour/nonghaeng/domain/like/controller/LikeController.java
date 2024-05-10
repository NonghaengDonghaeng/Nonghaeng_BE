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

    @GetMapping("/tour/{tourId}")
    public ResponseEntity<String> createLikeTour(Authentication authentication,
                                           @PathVariable("tourId") Long tourId) {

        User user = authService.toUserEntity(authentication);

        likeService.createLike(user, tourId, "tour");

        return new ResponseEntity<>("좋아요!", HttpStatus.CREATED);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<String> createLikeReview(Authentication authentication,
                                                 @PathVariable("reviewId") Long reviewId) {

        User user = authService.toUserEntity(authentication);

        likeService.createLike(user, reviewId, "review");

        return new ResponseEntity<>("좋아요!", HttpStatus.CREATED);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<String> createLikeRoom(Authentication authentication,
                                                 @PathVariable("roomId") Long roomId) {

        User user = authService.toUserEntity(authentication);

        likeService.createLike(user, roomId, "room");

        return new ResponseEntity<>("좋아요!", HttpStatus.CREATED);
    }

    @GetMapping("/experience/{experienceId}")
    public ResponseEntity<String> createLikeExperience(Authentication authentication,
                                                 @PathVariable("experienceId") Long experienceId) {

        User user = authService.toUserEntity(authentication);

        likeService.createLike(user, experienceId, "experience");

        return new ResponseEntity<>("좋아요!", HttpStatus.CREATED);
    }


}
