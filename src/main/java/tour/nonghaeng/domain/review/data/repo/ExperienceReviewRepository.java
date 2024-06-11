package tour.nonghaeng.domain.review.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.review.data.ExperienceReview;
import tour.nonghaeng.domain.review.data.Review;

import java.util.Optional;

@Repository
public interface ExperienceReviewRepository extends JpaRepository<ExperienceReview, Long> {


    @Query("select er from ExperienceReview er where er.id = :id")
    Optional<Review> findReviewById(@Param("id") Long id);

    @Query("select er from ExperienceReview er where er.experience.id = :expId")
    Page<Review> findReviewPageByExpId(@Param("expId") Long expId, Pageable pageable);

    @Query("select er from ExperienceReview er where er.user = :user")
    Page<Review> findReviewPageByUser(@Param("user") User user, Pageable pageable);
}
