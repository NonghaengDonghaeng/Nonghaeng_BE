package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.etc.enums.like.LikeType;
import tour.nonghaeng.domain.like.data.ReviewLike;
import tour.nonghaeng.domain.like.data.repo.ReviewLikeRepository;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.service.ReviewServiceImpl;
import tour.nonghaeng.global.auth.AuthValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewLikeService implements LikeService {

    private static final LikeType LIKE_TYPE = LikeType.REVIEW;

    private final ReviewLikeRepository reviewLikeRepository;

    private final ReviewServiceImpl reviewServiceImpl;

    private final AuthValidator authValidator;


    @Override
    public LikeType getType() {
        return LIKE_TYPE;
    }

    @Override
    public boolean clickLike(Member user, Long reviewId) {

        Review review = reviewServiceImpl.findById(reviewId);

        Optional<ReviewLike> maybeReviewLike = reviewLikeRepository.findByUserAndReview(authValidator.userValidate(user), review);

        return maybeReviewLike.map(this::offLike).orElseGet(() -> onLike(user, review));
    }

    private boolean onLike(Member user, Review review) {

        reviewLikeRepository.save(ReviewLike.builder()
                .user(authValidator.userValidate(user))
                .review(review)
                .build()
        );
        return true;
    }

    private boolean offLike(ReviewLike reviewLike) {

        reviewLikeRepository.delete(reviewLike);
        return false;
    }
}
