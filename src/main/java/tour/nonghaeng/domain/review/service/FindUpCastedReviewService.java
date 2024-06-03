package tour.nonghaeng.domain.review.service;

import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.data.Review;

public interface FindUpCastedReviewService {

    ReviewServiceType getType();

    Review findReviewById(Long reviewId);
}
