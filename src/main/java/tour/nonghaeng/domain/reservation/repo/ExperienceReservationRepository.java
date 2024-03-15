package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienceReservationRepository extends JpaRepository<ExperienceReservation, Long> {

    @Query("SELECT sum (er.numOfParticipant) from ExperienceReservation er where er.experienceRound = :experienceRound and er.reservationDate = :reservationDate and er.stateType != 'NOT_CONFIRM_RESERVATION' and er.stateType != 'CANCEL_RESERVATION'")
    Optional<Integer> countParticipantByExperienceRoundAndReservationDate(@Param("experienceRound")ExperienceRound experienceRound, @Param("reservationDate")LocalDate reservationDate);

    @Query("SELECT er from ExperienceReservation er where er.seller = :seller and er.stateType != 'CANCEL_RESERVATION'")
    Page<ExperienceReservation> findAllBySeller(@Param("seller") Seller seller, Pageable pageable);

    @Query("select er from ExperienceReservation er where er.user = :user")
    Page<ExperienceReservation> findAllByUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT er.seller from ExperienceReservation er where er.id = :id")
    Optional<Seller> findSellerById(@Param("id") Long experienceReservationId);

    @Query("SELECT er.user from ExperienceReservation er where er.id = :id")
    Optional<User> findUserById(@Param("id") Long experienceReservationId);

    boolean existsById(Long experienceReservationId);

}
