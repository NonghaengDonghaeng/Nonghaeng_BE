package tour.nonghaeng.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;

public interface ViewForEachEntityService {

    ReviewServiceType getType();

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable);

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable);

}
