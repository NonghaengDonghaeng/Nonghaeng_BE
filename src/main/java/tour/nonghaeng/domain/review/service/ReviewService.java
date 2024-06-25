package tour.nonghaeng.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSpecDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.LikesService;
import tour.nonghaeng.global.infra.service.ViewService;

public interface ReviewService extends CrudService<Review, CreateReviewDto>, ViewService<ReviewSummaryDto, ReviewDetailDto, ReviewSpecDto>, LikesService<Review> {


    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable, String type);

    Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable, String type);
}
