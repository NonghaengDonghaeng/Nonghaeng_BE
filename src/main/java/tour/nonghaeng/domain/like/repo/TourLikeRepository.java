package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.entity.TourLike;

@Repository
public interface TourLikeRepository extends JpaRepository<TourLike, Long> {

    @Query("select count(tl) from TourLike tl where tl.user.id = :userId and tl.tour.id = :tourId")
    int countByTourIdAndUserId(@Param("userId") Long userId, @Param("tourId") Long tourId);
}
