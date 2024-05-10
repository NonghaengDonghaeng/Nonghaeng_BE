package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.entity.TourLike;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.Optional;

@Repository
public interface TourLikeRepository extends JpaRepository<TourLike, Long> {

    int countByTour(Tour tour);

    Optional<TourLike> findByUserAndTour(User user, Tour tour);
}
