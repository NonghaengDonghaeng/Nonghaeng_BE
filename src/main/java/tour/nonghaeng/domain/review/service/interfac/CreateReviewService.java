package tour.nonghaeng.domain.review.service.interfac;

import tour.nonghaeng.domain.etc.review.ReviewServiceType;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;

public interface CreateReviewService {

    ReviewServiceType getType();

    Long createReview(Member user, Long reservationId, CreateReviewDto requestDto);
}
