package tour.nonghaeng.domain.review.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.ExperienceReview;
import tour.nonghaeng.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceReviewRepository extends JpaRepository<ExperienceReview, Long> {

    @Query("select er from ExperienceReview er where er.user = :user")
    List<Review> findReviewByUser(@Param("user") User user);

    @Query("select er from ExperienceReview er where er.experience = :experience")
    List<Review> findReviewByExperience(@Param("experience") Experience experience);

    @Query("select er from ExperienceReview er where er.id = :id")
    Optional<Review> findReviewById(@Param("id") Long id);

    @Query("select er from ExperienceReview er where er.user = :user")
    Page<Review> findReviewPageByUser(@Param("user") User user, Pageable pageable);
}
