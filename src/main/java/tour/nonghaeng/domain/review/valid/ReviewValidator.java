package tour.nonghaeng.domain.review.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.repo.ReviewRepository;
import tour.nonghaeng.domain.review.exception.ReviewException;
import tour.nonghaeng.domain.review.exception.error.ReviewErrorCode;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewValidator {

    private final ReviewRepository reviewRepository;

    public void ownerUserValidate(User user, Long reviewId) {

        idValidate(reviewId);

        if(!user.equals(reviewRepository.findUserById(reviewId).get())){
            throw new ReviewException(ReviewErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

    }

    private void idValidate( Long reviewId) {
        if(!reviewRepository.existsById(reviewId)) {
            throw new ReviewException(ReviewErrorCode.NO_EXIST_REVIEW_ID);
        }
    }
}
