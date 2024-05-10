package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.like.entity.ReviewLike;
import tour.nonghaeng.domain.like.repo.ReviewLikeRepository;
import tour.nonghaeng.domain.like.valid.ReviewLikeValidator;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.service.ReviewService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;

    private final ReviewService reviewService;

    private final ReviewLikeValidator reviewLikeValidator;


    public void createLike(User user, Long reviewId) {

        Review review = reviewService.findById(reviewId);

        reviewLikeValidator.createLikeValidate(user.getId(), reviewId);

        reviewLikeRepository.save(ReviewLike.builder().user(user).review(review).build());
    }
}
