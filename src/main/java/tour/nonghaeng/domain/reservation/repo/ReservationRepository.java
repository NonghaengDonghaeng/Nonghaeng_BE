package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.Reservation;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsById(Long reservationId);

    @Query("select r.user from Reservation r where r.id = :id")
    Optional<User> findUserById(@Param("id") Long reservationId);

    @Query("select r.seller from Reservation r where r.id = :id")
    Optional<Seller> findSellerById(@Param("id") Long reservationId);

    @Query("select r.dType from Reservation r where r.id = :id")
    Optional<String> findReservationType(@Param("id") Long reservationId);
    
}
