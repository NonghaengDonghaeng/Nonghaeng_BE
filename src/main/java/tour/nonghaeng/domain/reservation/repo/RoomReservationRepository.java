package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    @Query("select sum(rr.numOfRoom) from RoomReservation rr join rr.reservationDates rd where rr.room = :room and rd.reservationDate = :reservationDate")
    Optional<Integer> countByRoomAndReservationDate(@Param("room") Room room, @Param("reservationDate") LocalDate reservationDate);

    boolean existsById(Long roomReservationId);

}
