package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.specification.ReviewSpecification;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.data.repo.ReviewRepository;
import tour.nonghaeng.domain.review.service.registry.CreateReviewServiceRegistry;
import tour.nonghaeng.domain.review.service.registry.FindUpCastedReviewServiceRegistry;
import tour.nonghaeng.domain.review.service.valid.ReviewValidator;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.auth.AuthValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final TourService tourService;

    private final ReviewValidator reviewValidator;
    private final AuthValidator authValidator;
    private final ReservationService reservationService;


    private final CreateReviewServiceRegistry createReviewServiceRegistry;
    private final FindUpCastedReviewServiceRegistry findUpCastedReviewServiceRegistry;



    @Override
    public ReviewServiceType getType() {
        return ReviewServiceType.TOUR_AND_ALL;
    }


    private Review findReviewById(Long reviewId) {

        String type = reviewRepository.findReviewTypeById(reviewId);

        FindUpCastedReviewService service = findUpCastedReviewServiceRegistry.getService(type);

        return service.findReviewById(reviewId);
    }


    //Tour id 조회시 나타나는 구현체
    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable) {

        Specification<Review> specification = ReviewSpecification.buildSpecification(tourService.findById(id).getName(), null);

        Page<Review> reviewPage = reviewRepository.findAll(specification, pageable).map(review -> findReviewById(review.getId()));

        return reviewPage.map(Review::toReviewSummaryDto);
    }

    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable) {

        Page<Review> reviewPage = reviewRepository.findReviewPageByUser(authValidator.userValidate(user), pageable)
                .map(review -> findReviewById(review.getId()));

        if(!reviewPage.hasContent()) {
            return Page.empty();
        }

        return reviewPage.map(Review::toReviewSummaryDto);
    }

    public Long create(Member user, Long reservationId, CreateReviewDto requestDto) {

        String type = reservationService.findTypeById(reservationId);

        reviewValidator.createReviewValidate(user,reservationId,type);

        CreateReviewService service = createReviewServiceRegistry.getService(type);

        return service.createReview(user, reservationId, requestDto);
    }


    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디"));
    }


    //리뷰 조회 서비스
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByKeyword(Pageable pageable, String title, String content) {

        Specification<Review> specification = ReviewSpecification.buildSpecification(title, content);

        Page<Review> reviewPage = reviewRepository.findAll(specification, pageable);

        Page<Review> upCastedReviewPage = reviewPage.map(review -> findReviewById(review.getId()));

        return upCastedReviewPage.map(Review::toReviewSummaryDto);
    }

    public ReviewDetailDto getReviewDetailDto(Long reviewId) {

        reviewValidator.idValidate(reviewId);

        Review review = findReviewById(reviewId);

        return review.toReviewDetailDto();
    }
}
