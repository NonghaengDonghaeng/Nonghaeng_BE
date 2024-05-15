package tour.nonghaeng.domain.review.service.interfac;

import tour.nonghaeng.domain.etc.review.ReviewServiceType;
import tour.nonghaeng.domain.review.entity.Review;

public interface FindUpCastedReviewService {

    ReviewServiceType getType();

    Review findReviewById(Long reviewId);
}
