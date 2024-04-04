package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    @Query("select sum(rr.numOfRoom) from RoomReservation rr join rr.reservationDates rd where rr.room = :room and rd.reservationDate = :reservationDate")
    Optional<Integer> countByRoomAndReservationDate(@Param("room") Room room, @Param("reservationDate") LocalDate reservationDate);

    @Query("select min(rrd.reservationDate) from RoomReservationDate rrd where rrd.roomReservation.id = :id")
    Optional<LocalDate> findStartDateById(@Param("id") Long id);

    @Query("select max(rrd.reservationDate) from RoomReservationDate rrd where rrd.roomReservation.id = :id")
    Optional<LocalDate> findEndDateById(@Param("id") Long id);

    boolean existsById(Long roomReservationId);

    @Query("select rr from RoomReservation rr where rr.user = :user")
    Page<Reservation> findReservationPageByUser(@Param("user") User user, Pageable pageable);

    @Query("select rr from RoomReservation rr where rr.seller =:seller")
    Page<Reservation> findReservationPageBySeller(@Param("seller") Seller seller, Pageable pageable);

    @Query("select rr from RoomReservation rr where rr.id = :id")
    Optional<Reservation> findReservationById(@Param("id") Long reservationId);


}
