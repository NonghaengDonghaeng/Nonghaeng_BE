package tour.nonghaeng.domain.review.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.entity.RoomReview;
import tour.nonghaeng.domain.room.entity.Room;

import java.util.List;

@Repository
public interface RoomReviewRepository extends JpaRepository<RoomReview,Long> {

    @Query("select rr from RoomReview rr where rr.user = :user")
    List<Review> findReviewByUser(@Param("user") User user);

    @Query("select rr from RoomReview rr where rr.room= :room")
    List<Review> findReviewByRoom(@Param("room")Room room);
}
