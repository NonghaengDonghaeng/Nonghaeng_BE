package tour.nonghaeng.domain.tour.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {

    Optional<Tour> findBySeller(Seller seller);

    boolean existsBySeller(Seller seller);

    boolean existsById(Long tourId);

    Page<Tour> findAll(Pageable pageable);

    Page<Tour> findAllByRoomsIsNotEmpty(Pageable pageable);

}
