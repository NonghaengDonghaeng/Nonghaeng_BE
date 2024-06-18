package tour.nonghaeng.domain.reservation.data.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.data.ExperienceReservation;
import tour.nonghaeng.domain.reservation.data.Reservation;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienceReservationRepository extends JpaRepository<ExperienceReservation, Long> {

    boolean existsById(Long experienceReservationId);


    @Query("SELECT sum (er.numOfParticipant) from ExperienceReservation er where er.experienceRound = :experienceRound and er.reservationDate = :reservationDate and er.stateType != 'NOT_CONFIRM_RESERVATION' and er.stateType != 'CANCEL_RESERVATION' and er.stateType != 'TMP_RESERVATION'")
    Optional<Integer> countParticipantByExperienceRoundAndReservationDate(@Param("experienceRound")ExperienceRound experienceRound, @Param("reservationDate")LocalDate reservationDate);


    @Query("select er from ExperienceReservation er where er.user = :user and er.stateType != 'TMP_RESERVATION'")
    Page<Reservation> findReservationPageByUser(@Param("user") User user, Pageable pageable);

    @Query("select er from ExperienceReservation er where er.seller = :seller and er.stateType != 'TMP_RESERVATION'")
    Page<Reservation> findReservationPageBySeller(@Param("seller") Seller seller, Pageable pageable);


    @Query("select er from ExperienceReservation er where er.id = :id")
    Optional<Reservation> findReservationById(@Param("id") Long reservationId);

}
