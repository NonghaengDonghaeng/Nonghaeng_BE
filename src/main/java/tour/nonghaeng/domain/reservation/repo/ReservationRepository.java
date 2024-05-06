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

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsById(Long reservationId);

    @Query("select r.user from Reservation r where r.id = :id")
    Optional<User> findUserById(@Param("id") Long reservationId);

    @Query("select r.seller from Reservation r where r.id = :id")
    Optional<Seller> findSellerById(@Param("id") Long reservationId);

    @Query(value = "SELECT dtype FROM reservations where reservation_id = :id", nativeQuery = true)
    String findReservationType(@Param("id") Long reservationId);

    @Query("select r from Reservation r where r.stateType = 'CONFIRM_RESERVATION'")
    Optional<List<Reservation>> findAllConfirmReservation();

    @Query("select r from Reservation r where r.stateType = 'WAITING_RESERVATION'")
    Optional<List<Reservation>> findAllWaitingReservation();

    @Query("select r from Reservation r where r.user = :user")
    Page<Reservation> findReservationPageByUser(@Param("user") User user, Pageable pageable);

    @Query("select r from Reservation r where r.user = :user")
    Optional<List<Reservation>> findReservationsByUser(@Param("user") User user);
}
