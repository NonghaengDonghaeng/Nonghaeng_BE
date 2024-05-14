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
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.service.ReviewService;
import tour.nonghaeng.domain.review.service.ReviewServiceImpl;
import tour.nonghaeng.domain.review.service.registry.ReviewServiceRegistry;
import tour.nonghaeng.domain.review.valid.ReviewValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;
    private final AuthService authService;

    private final ReviewValidator reviewValidator;

    private final ReviewServiceRegistry reviewServiceRegistry;



    @GetMapping
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getReviewSummaryDtoPage(@PageableDefault(size = 10) Pageable pageable,
                                                                          @RequestParam(name = "title",required = false)String title,
                                                                          @RequestParam(name="content",required = false)String content) {

        Page<? extends ReviewSummaryDto> dtoPage =
                reviewServiceImpl.getReviewSummaryDtoPageByKeyword(pageable, title, content);

        return new ResponseEntity<>(dtoPage,HttpStatus.OK);
    }


    @PostMapping("/{reservationId}")
    public ResponseEntity<String> createReview(Authentication authentication,
                                                         @PathVariable("reservationId") Long reservationId,
                                                         @RequestBody CreateReviewDto requestDto) {

        Member user = authService.toMemberEntity(authentication);

        Long reviewId = reviewServiceImpl.create(user, reservationId, requestDto);

        return new ResponseEntity<>(reviewId + "리뷰 생성완료", HttpStatus.OK);
    }


    //상세조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReviewDetailDto(@PathVariable("reviewId") Long reviewId) {

        ReviewDetailDto reviewDetailDto =
                reviewServiceImpl.getReviewDetailDto(reviewId);

        return new ResponseEntity<>(reviewDetailDto, HttpStatus.OK);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getReviewSummaryDtoPage(@PathVariable("type") String type,
                                                                                    @PathVariable("id") Long id, Pageable pageable) {

        ReviewService service = reviewServiceRegistry.getService(type);

        Page<? extends ReviewSummaryDto> summaryDtoPage = service.getReviewSummaryDtoPageById(id, pageable);

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }


    @GetMapping("/my-review")
    public ResponseEntity<Page<? extends ReviewSummaryDto>> getMyReviewSummaryDtoPage(Authentication authentication,
                                                                                      Pageable pageable,
                                                                                      @RequestParam(value = "type", defaultValue = "all", required = false) String type) {

        ReviewService service = reviewServiceRegistry.getService(type);

        Member user = authService.toMemberEntity(authentication);

        Page<? extends ReviewSummaryDto> summaryDtoPage = service.getReviewSummaryDtoPageByUser(user, pageable);

        return new ResponseEntity<>(summaryDtoPage, HttpStatus.OK);
    }
}
