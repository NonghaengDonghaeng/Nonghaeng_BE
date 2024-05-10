package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.entity.ReviewLike;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    @Query("select count(rl) from ReviewLike rl where rl.user.id = :userId and rl.review.id = :reviewId")
    int countByReviewIdAndUserId(@Param("userId") Long userId, @Param("reviewId") Long reviewID);
}
