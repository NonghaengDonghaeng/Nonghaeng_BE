package tour.nonghaeng.domain.review.service;

import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;

public interface CreateReviewService {

    ReviewServiceType getType();

    Long createReview(Member user, Long reservationId, CreateReviewDto requestDto);
}
