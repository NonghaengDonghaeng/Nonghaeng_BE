package tour.nonghaeng.domain.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.service.ReviewService;
import tour.nonghaeng.global.auth.auth.service.AuthService;
import tour.nonghaeng.domain.review.valid.ReviewValidator;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    private final ReviewValidator reviewValidator;


    @GetMapping
    public ResponseEntity<Page<ReviewSummaryDto>> showReviewSummaryDtoPage(@PageableDefault(size=10) Pageable pageable,
                                                                           @RequestParam(name="title",required = false)String title,
                                                                           @RequestParam(name="content",required = false)String content) {

    }

    //TODO: 예약을 보고 아무나 리뷰쓰지 못하도록 검증만들기
    @PostMapping("/experience")
    public ResponseEntity<String> createExperienceReview(Authentication authentication,
                                                         @RequestBody CreateExpReviewDto requestDto) {

        User user = authService.toUserEntity(authentication);

        Long expReviewId = reviewService.createExpReview(user, requestDto);

        return new ResponseEntity<>(expReviewId + "체험 리뷰 생성완료", HttpStatus.OK);
    }

    @PostMapping("/room")
    public ResponseEntity<String> createRoomReview(Authentication authentication,
                                                   @RequestBody CreateRoomReviewDto requestDto) {

        User user = authService.toUserEntity(authentication);

        Long roomReviewId = reviewService.createRoomReview(user, requestDto);

        return new ResponseEntity<>(roomReviewId + "숙소 리뷰 생성완료", HttpStatus.OK);
    }


    //파리미터로 타입으로 분류할지 그냥 url에 어떤 타입인지로 컨트롤러안 매핑함수를 2개로 만들지 고민중
    @GetMapping("/{id}")
    public ResponseEntity<List<ReviewSummaryDto>> getReviewSummary(Authentication authentication,
                                                                   @PathVariable("id") Long id,
                                                                   @RequestParam(value = "type",defaultValue = "room") String type) {
        User user = authService.toUserEntity(authentication);

        List<ReviewSummaryDto> reviewList = reviewService.findReviewList(user, id, type);

        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }
}
