package tour.nonghaeng.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.etc.review.ReviewServiceType;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.entity.Review;

public interface ReviewService {

    ReviewServiceType getType();

    Review findReviewById(Long reviewId);

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable);

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable);

    Long createReview(Member user, Long reservationId, CreateReviewDto requestDto);
}
