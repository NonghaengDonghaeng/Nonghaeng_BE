package tour.nonghaeng.domain.review.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r.user from Review r where r.id = :id")
    Optional<User> findUserById(@Param("id") Long reviewId);


    @Query(value = "SELECT dtype FROM reviews where review_id = :id", nativeQuery = true)
    String findReviewTypeById(@Param("id") Long reviewId);

    @Query("select r from Review r where r.user = :user")
    Page<Review> findReviewPageByUser(@Param("user") User user, Pageable pageable);


    Page<Review> findAll(Specification<Review> spec, Pageable pageable);
}
