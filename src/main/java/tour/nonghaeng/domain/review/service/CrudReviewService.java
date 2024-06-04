package tour.nonghaeng.domain.review.service;

import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.global.infra.service.CrudService;

public interface CrudReviewService extends CrudService<Review, CreateReviewDto> {

    ReviewServiceType getType();

}
