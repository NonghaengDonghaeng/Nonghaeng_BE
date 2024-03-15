package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    boolean existsById(Long roomReservationId);

}
