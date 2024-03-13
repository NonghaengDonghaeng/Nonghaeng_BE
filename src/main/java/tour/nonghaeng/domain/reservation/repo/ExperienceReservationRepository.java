package tour.nonghaeng.domain.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

@Repository
public interface ExperienceReservationRepository extends JpaRepository<ExperienceReservation, Long> {

    boolean existsById(Long experienceReservationId);
}
