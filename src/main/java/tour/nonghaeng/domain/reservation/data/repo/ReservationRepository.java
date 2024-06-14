package tour.nonghaeng.domain.reservation.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.data.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsById(Long reservationId);


    @Query("select r.user from Reservation r where r.id = :id")
    Optional<User> findUserById(@Param("id") Long reservationId);

    @Query("select r.seller from Reservation r where r.id = :id")
    Optional<Seller> findSellerById(@Param("id") Long reservationId);


    //조회시 사용
    @Query(value = "SELECT dtype FROM reservations where reservation_id = :id", nativeQuery = true)
    String findReservationTypeById(@Param("id") Long reservationId);

    @Query("select r from Reservation r where r.user = :user")
    Page<Reservation> findReservationPageByUser(@Param("user") User user, Pageable pageable);

    @Query("select r from Reservation r where r.seller = :seller")
    Page<Reservation> findReservationPageBySeller(@Param("seller") Seller seller, Pageable pageable);


    //스케줄링 서비스에서 사용
    @Query("select r from Reservation r where r.stateType = 'CONFIRM_RESERVATION'")
    Optional<List<Reservation>> findAllConfirmReservation();

    @Query("select r from Reservation r where r.stateType = 'WAITING_RESERVATION'")
    Optional<List<Reservation>> findAllWaitingReservation();

    //결제관련
    @Query("select r from Reservation r" +
            " left join fetch r.payment p" +
            " left join fetch r.user u" +
            " where r.reservationUid = :reservationUid")
    Optional<Reservation> findReservationAndPaymentAndMember(String reservationUid);

    @Query("select r from Reservation r" +
            " left join fetch r.payment p" +
            " where r.reservationUid = :reservationUid")
    Optional<Reservation> findReservationAndPayment(String reservationUid);

}
