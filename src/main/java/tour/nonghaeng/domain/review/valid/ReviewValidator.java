package tour.nonghaeng.domain.review.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.valid.ReservationValidator;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.exception.ReviewException;
import tour.nonghaeng.domain.review.exception.error.ReviewErrorCode;
import tour.nonghaeng.domain.review.repo.ReviewRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewValidator {

    private final ReviewRepository reviewRepository;
    private final ReservationValidator reservationValidator;

    public void ownerValidate(Member user, Long reviewId) {

        idValidate(reviewId);

        if(!((User) user).equals(reviewRepository.findUserById(reviewId).get())){

            throw new ReviewException(ReviewErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

    }

    //예약당 한개의 리뷰인지 확인하는 검증 추가하기
    public void createReviewValidate(Member user, Long reservationId, String type) {

        reservationValidator.ownerUserValidate(user,reservationId);

        reservationValidator.checkReservationType(reservationId,type);

        reservationValidator.checkCompleteState(reservationId);

        if (!reviewRepository.isEmptyByReservation(reservationId)) {

            throw new ReviewException(ReviewErrorCode.ALREADY_EXISTED_REVIEW);
        }
    }

    public void dtypeValid(String dtype){
        if (!(dtype.equals("room") || dtype.equals("experience"))) {

            throw new ReviewException(ReviewErrorCode.WRONG_DTYPE_ERROR);
        }
    }

    public void idValidate( Long reviewId) {
        if(!reviewRepository.existsById(reviewId)) {
            throw new ReviewException(ReviewErrorCode.NO_EXIST_REVIEW_ID);
        }
    }

    public void pageValidate(Page<? extends Review> page) {

        if (page.isEmpty()) {
            throw new ReviewException(ReviewErrorCode.NO_REVIEW_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }
}
