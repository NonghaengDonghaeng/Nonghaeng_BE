package tour.nonghaeng.domain.review.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.entity.RoomReview;
import tour.nonghaeng.domain.room.entity.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomReviewRepository extends JpaRepository<RoomReview, Long> {

    @Query("select rr from RoomReview rr where rr.user = :user")
    List<Review> findReviewByUser(@Param("user") User user);

    @Query("select rr from RoomReview rr where rr.room= :room")
    List<Review> findReviewByRoom(@Param("room") Room room);

    @Query("select rr from RoomReview rr where rr.id = :id")
    Optional<Review> findReviewById(@Param("id") Long id);

    @Query("select rr from RoomReview rr where rr.user = :user")
    Page<Review> findReviewPageByUser(@Param("user") User user, Pageable pageable);
}
