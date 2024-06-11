package tour.nonghaeng.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.global.infra.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;

public interface SubReviewService extends CrudService<Review, CreateReviewDto> {

    ReviewServiceType getType();

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable);

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable);

}
