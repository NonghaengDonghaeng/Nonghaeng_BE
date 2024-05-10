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
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.service.ReviewService;
import tour.nonghaeng.domain.review.valid.ReviewValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    private final ReviewValidator reviewValidator;



    @GetMapping
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getReviewSummaryDtoPage(@PageableDefault(size = 10) Pageable pageable,
                                                                          @RequestParam(name = "title",required = false)String title,
                                                                          @RequestParam(name="content",required = false)String content) {

        Page<? extends ReviewSummaryDto> dtoPage = reviewService.getReviewSummaryDtoPageByKeyword(pageable, title, content);

        return new ResponseEntity<>(dtoPage,HttpStatus.OK);
    }


    @PostMapping("/experience/{reservationId}")
    public ResponseEntity<String> createExperienceReview(Authentication authentication,
                                                         @PathVariable("reservationId") Long reservationId,
                                                         @RequestBody CreateExpReviewDto requestDto) {

        User user = authService.toUserEntity(authentication);

        reviewValidator.createReviewValidate(user,reservationId,"experience");

        Long expReviewId = reviewService.createExpReview(user, reservationId, requestDto);

        return new ResponseEntity<>(expReviewId + "체험 리뷰 생성완료", HttpStatus.OK);
    }


    @PostMapping("/room/{reservationId}")
    public ResponseEntity<String> createRoomReview(Authentication authentication,
                                                   @PathVariable("reservationId") Long reservationId,
                                                   @RequestBody CreateRoomReviewDto requestDto) {

        User user = authService.toUserEntity(authentication);

        reviewValidator.createReviewValidate(user,reservationId,"room");

        Long roomReviewId = reviewService.createRoomReview(user, reservationId, requestDto);

        return new ResponseEntity<>(roomReviewId + "숙소 리뷰 생성완료", HttpStatus.OK);
    }


    //상세조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReviewDetailDto(@PathVariable("reviewId") Long reviewId) {

        ReviewDetailDto reviewDetailDto =
                reviewService.getReviewDetailDto(reviewId);

        return new ResponseEntity<>(reviewDetailDto, HttpStatus.OK);
    }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getRoomReviewSummaryDtoPage(@PathVariable("roomId") Long roomId, Pageable pageable) {

        Page<? extends ReviewSummaryDto> summaryDtoPage =
                reviewService.getReviewSummaryDtoPage(roomId, pageable, "room");

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }


    @GetMapping("/experience/{expId}")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getExperienceReviewSummaryDtoPage(@PathVariable("expId") Long expId, Pageable pageable) {

        Page<? extends ReviewSummaryDto> summaryDtoPage =
                reviewService.getReviewSummaryDtoPage(expId, pageable, "experience");

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }


    @GetMapping("/tour/{tourId}")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getTourReviewSummaryDtoPage(@PathVariable("tourId") Long tourId, Pageable pageable) {

        Page<? extends ReviewSummaryDto> summaryDtoPage =
                reviewService.getReviewSummaryDtoPage(tourId, pageable, "tour");

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }

    @GetMapping("/my-review")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getMyReviewSummaryDtoPage(Authentication authentication,
                                                                                      Pageable pageable,
                                                                                      @RequestParam(value = "type", defaultValue = "all", required = false) String type) {

        User user = authService.toUserEntity(authentication);

        Page<? extends ReviewSummaryDto> summaryDtoPage =
                reviewService.getReviewSummaryDtoPageByUser(user, pageable, type);

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }
}
