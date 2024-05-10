package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.entity.ReviewLike;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;

import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    int countByReview(Review review);

    Optional<ReviewLike> findByUserAndReview(User user, Review review);
}
