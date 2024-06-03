package tour.nonghaeng.domain.like.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.data.TourLike;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.tour.data.Tour;

import java.util.Optional;

@Repository
public interface TourLikeRepository extends JpaRepository<TourLike, Long> {

    int countByTour(Tour tour);

    Optional<TourLike> findByUserAndTour(User user, Tour tour);
}
