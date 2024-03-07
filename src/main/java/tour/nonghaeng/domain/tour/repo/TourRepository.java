package tour.nonghaeng.domain.tour.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {

    Optional<Tour> findBySeller(Seller seller);

    boolean existsBySeller(Seller seller);
}
