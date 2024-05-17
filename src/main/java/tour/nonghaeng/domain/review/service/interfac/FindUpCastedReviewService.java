package tour.nonghaeng.domain.review.service.interfac;

import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.entity.Review;

public interface FindUpCastedReviewService {

    ReviewServiceType getType();

    Review findReviewById(Long reviewId);
}
