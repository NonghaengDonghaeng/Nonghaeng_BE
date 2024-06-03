package tour.nonghaeng.domain.review.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.data.RoomReview;

import java.util.Optional;

@Repository
public interface RoomReviewRepository extends JpaRepository<RoomReview, Long> {


    @Query("select rr from RoomReview rr where rr.id = :id")
    Optional<Review> findReviewById(@Param("id") Long id);

    @Query("select rr from RoomReview rr where rr.room.id = :roomId")
    Page<Review> findReviewPageByRoomId(@Param("roomId") Long roomId, Pageable pageable);

    @Query("select rr from RoomReview rr where rr.user = :user")
    Page<Review> findReviewPageByUser(@Param("user") User user, Pageable pageable);
}
