package tour.nonghaeng.domain.review.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSpecDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.service.ReviewService;
import tour.nonghaeng.global.auth.AuthService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;


    @GetMapping
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getReviewSummaryDtoPage(@PageableDefault(size = 10) Pageable pageable,
                                                                                    @ModelAttribute ReviewSpecDto reviewSpecDto) {

        Page<? extends ReviewSummaryDto> dtoPage =
                reviewService.getSummaryDtoPage(pageable, reviewSpecDto);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createReview(Authentication authentication,
                                               @RequestBody CreateReviewDto requestDto) {

        Member user = authService.toMemberEntity(authentication);

        Long reviewId = reviewService.create(user, requestDto).getId();

        return new ResponseEntity<>("리뷰 생성완료(id:"+reviewId+")", HttpStatus.CREATED);
    }


    //상세조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReviewDetailDto(@PathVariable("reviewId") Long reviewId) {

        ReviewDetailDto reviewDetailDto =
                reviewService.getDetailDto(reviewId);

        return new ResponseEntity<>(reviewDetailDto, HttpStatus.OK);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getReviewSummaryDtoPage(@PathVariable("type") String type,
                                                                                    @PathVariable("id") Long id, Pageable pageable) {


        Page<? extends ReviewSummaryDto> summaryDtoPage =
                reviewService.getReviewSummaryDtoPageById(id, pageable, type);

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }


    @GetMapping("/my-review")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getMyReviewSummaryDtoPage(Authentication authentication,
                                                                                      Pageable pageable,
                                                                                      @RequestParam(value = "type", defaultValue = "all", required = false) String type) {

        Member user = authService.toMemberEntity(authentication);

        Page<? extends ReviewSummaryDto> summaryDtoPage =
                reviewService.getReviewSummaryDtoPageByUser(user, pageable, type);

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }
}
