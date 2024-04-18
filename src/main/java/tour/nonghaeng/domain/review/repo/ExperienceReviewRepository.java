package tour.nonghaeng.domain.review.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.review.entity.ExperienceReview;

@Repository
public interface ExperienceReviewRepository extends JpaRepository<ExperienceReview, Long> {

}
