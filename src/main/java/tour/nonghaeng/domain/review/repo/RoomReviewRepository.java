package tour.nonghaeng.domain.review.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.review.entity.RoomReview;

@Repository
public interface RoomReviewRepository extends JpaRepository<RoomReview,Long> {
}
