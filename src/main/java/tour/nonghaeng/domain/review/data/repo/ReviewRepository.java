package tour.nonghaeng.domain.review.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.review.data.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r.user from Review r where r.id = :id")
    Optional<User> findUserById(@Param("id") Long reviewId);


    @Query(value = "SELECT dtype FROM reviews where review_id = :id", nativeQuery = true)
    String findReviewTypeById(@Param("id") Long reviewId);

    @Query("select r from Review r where r.user = :user")
    Page<Review> findReviewPageByUser(@Param("user") User user, Pageable pageable);

    @Query("select count(r)=0 from Review r where r.reservation.id = :reservationId")
    boolean isEmptyByReservation(@Param("reservationId") Long reservationId);

    Page<Review> findAll(Specification<Review> spec, Pageable pageable);
}
