package tour.nonghaeng.domain.review.service;

import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSpecDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.ViewService;

public interface ReviewService extends CrudService<Review, CreateReviewDto>, ViewService<ReviewSummaryDto, ReviewDetailDto, ReviewSpecDto>, ViewForEachEntityService {


}
