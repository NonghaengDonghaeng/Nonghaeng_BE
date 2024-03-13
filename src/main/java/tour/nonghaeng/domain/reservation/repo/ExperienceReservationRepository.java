package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienceReservationRepository extends JpaRepository<ExperienceReservation, Long> {

    @Query("SELECT sum (er.numOfParticipant) from ExperienceReservation er where er.experienceRound = :experienceRound and er.reservationDate = :reservationDate")
    Optional<Integer> countParticipantByExperienceRoundAndReservationDate(@Param("experienceRound")ExperienceRound experienceRound, @Param("reservationDate")LocalDate reservationDate);

    boolean existsById(Long experienceReservationId);
}
