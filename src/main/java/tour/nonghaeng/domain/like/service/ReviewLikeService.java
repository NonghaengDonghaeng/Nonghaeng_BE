package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.etc.like.LikeType;
import tour.nonghaeng.domain.like.entity.ReviewLike;
import tour.nonghaeng.domain.like.repo.ReviewLikeRepository;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.service.ReviewService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewLikeService implements LikeService {

    private static final LikeType LIKE_TYPE = LikeType.REVIEW;

    private final ReviewLikeRepository reviewLikeRepository;

    private final ReviewService reviewService;


    @Override
    public LikeType getType() {
        return LIKE_TYPE;
    }

    @Override
    public boolean clickLike(Member user, Long reviewId) {

        Review review = reviewService.findById(reviewId);

        Optional<ReviewLike> maybeReviewLike = reviewLikeRepository.findByUserAndReview((User) user, review);

        return maybeReviewLike.map(this::offLike).orElseGet(() -> onLike(user, review));
    }

    private boolean onLike(Member user, Review review) {

        reviewLikeRepository.save(ReviewLike.builder().user((User) user).review(review).build());
        return true;
    }

    private boolean offLike(ReviewLike reviewLike) {

        reviewLikeRepository.delete(reviewLike);
        return false;
    }
}
