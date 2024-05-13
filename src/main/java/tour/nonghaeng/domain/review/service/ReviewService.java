package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.dto.specification.ReviewSpecification;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.repo.ReviewRepository;
import tour.nonghaeng.domain.review.valid.ReviewValidator;
import tour.nonghaeng.domain.tour.service.TourService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final RoomReviewService roomReviewService;
    private final ExperienceReviewService experienceReviewService;
    private final ReservationService reservationService;
    private final TourService tourService;

    private final ReviewValidator reviewValidator;




    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디"));
    }



    //리뷰 생성 서비스( createReview 로 통합되면 통합하기)
    public Long createExpReview(Member user, Long reservationId, CreateExpReviewDto requestDto) {

        Reservation reservation = reservationService.findById(reservationId);

        return experienceReviewService.createExperienceReview(user, reservation, requestDto);
    }


    public Long createRoomReview(Member user, Long reservationId, CreateRoomReviewDto requestDto) {

        Reservation reservation = reservationService.findById(reservationId);

        return roomReviewService.createRoomReview(user, reservation, requestDto);
    }


    //리뷰 조회 서비스
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByKeyword(Pageable pageable, String title, String content) {

        Specification<Review> specification = ReviewSpecification.buildSpecification(title, content);

        Page<Review> reviewPage = reviewRepository.findAll(specification, pageable);

        Page<Review> upCastedReviewPage = reviewPage.map(review -> findUpCastedReviewById(review.getId()));

        return upCastedReviewPage.map(Review::toReviewSummaryDto);
    }



    public ReviewDetailDto getReviewDetailDto(Long reviewId) {

        reviewValidator.idValidate(reviewId);

        Review review = findUpCastedReviewById(reviewId);

        return review.toReviewDetailDto();
    }



    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPage(Long id, Pageable pageable, String type) {

        Page<Review> reviewPage = getReviewPageByIdAndType(id, pageable, type);

        reviewValidator.pageValidate(reviewPage);

        return reviewPage.map(Review::toReviewSummaryDto);
    }

    private Page<Review> getReviewPageByIdAndType(Long id, Pageable pageable,String type) {

        if (type.equals("room")) {

            return roomReviewService.findReviewPageByRoomId(id, pageable);

        }
        else if (type.equals("experience")) {

            return experienceReviewService.findReviewPageByExpId(id, pageable);
        }

        return findReviewPageByTour(id, pageable);
    }

    private Page<Review> findReviewPageByTour(Long id, Pageable pageable) {

        Specification<Review> specification = ReviewSpecification.buildSpecification(tourService.findById(id).getName(), null);

        Page<Review> reviewPage = reviewRepository.findAll(specification, pageable);

        return reviewPage.map(review -> findUpCastedReviewById(review.getId()));
    }


    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable,String type) {

        Page<Review> reviewPage = findReviewPageByUser(user, pageable,type);

        if(!reviewPage.hasContent()) {
            return Page.empty();
        }

        return reviewPage.map(Review::toReviewSummaryDto);
    }

    private Page<Review> findReviewPageByUser(Member user, Pageable pageable,String type) {

        if(type.equals("room")) {
            return roomReviewService.findReviewPageByUser(user, pageable);
        }
        if(type.equals("experience")) {
            return experienceReviewService.findReviewPageByUser(user, pageable);
        }
        return reviewRepository.findReviewPageByUser((User) user, pageable)
                .map(review -> findUpCastedReviewById(review.getId()));
    }


    private Review findUpCastedReviewById(Long reviewId) {

        String dtype = reviewRepository.findReviewTypeById(reviewId);

        reviewValidator.dtypeValid(dtype);

        if (dtype.equals("room")) {

            return roomReviewService.findReviewById(reviewId);
        }

        return experienceReviewService.findReviewById(reviewId);
    }




}
