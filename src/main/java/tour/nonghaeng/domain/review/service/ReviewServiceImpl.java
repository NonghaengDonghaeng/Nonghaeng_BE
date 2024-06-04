package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.data.repo.ReviewRepository;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSpecDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.service.registry.CrudReviewServiceRegistry;
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
    private final ReservationService reservationService;

    private final ReviewValidator reviewValidator;
    private final AuthValidator authValidator;


    private final CrudReviewServiceRegistry crudReviewServiceRegistry;



    //ViewForEachEntityService
    @Override
    public ReviewServiceType getType() {
        return ReviewServiceType.TOUR_AND_ALL;
    }


    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable) {

        ReviewSpecDto specDto = ReviewSpecDto.builder()
                .title(tourService.findById(id).getName())
                .build();

        Page<Review> reviewPage = reviewRepository.findAll(specDto.buildSpecification(), pageable).map(review -> findById(review.getId()));

        return reviewPage.map(Review::toReviewSummaryDto);
    }


    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable) {

        Page<Review> reviewPage = reviewRepository.findReviewPageByUser(authValidator.userValidate(user), pageable)
                .map(review -> findById(review.getId()));

        if(!reviewPage.hasContent()) {
            return Page.empty();
        }

        return reviewPage.map(Review::toReviewSummaryDto);
    }


    //CrudService
    @Override
    public Long create(Member user, CreateReviewDto requestDto) {

        Long reservationId = requestDto.getReservationId();

        String type = reservationService.findTypeById(reservationId);

        reviewValidator.createReviewValidate(user,reservationId,type);

        CrudReviewService service = crudReviewServiceRegistry.getService(type);

        return service.create(user,requestDto);
    }

    @Override
    public Review findById(Long reviewId) {
        String type = reviewRepository.findReviewTypeById(reviewId);

        CrudReviewService service = crudReviewServiceRegistry.getService(type);

        return service.findById(reviewId);
    }


    //ViewService
    @Override
    public Page<ReviewSummaryDto> getSummaryDtoPage(Pageable pageable, ReviewSpecDto specDto) {


        Page<Review> reviewPage = reviewRepository.findAll(specDto.buildSpecification(), pageable);

        Page<Review> upCastedReviewPage = reviewPage.map(review -> findById(review.getId()));

        return upCastedReviewPage.map(Review::toReviewSummaryDto);
    }

    @Override
    public ReviewDetailDto getDetailDto(Long reviewId) {

        reviewValidator.idValidate(reviewId);

        Review review = findById(reviewId);

        return review.toReviewDetailDto();
    }

}
