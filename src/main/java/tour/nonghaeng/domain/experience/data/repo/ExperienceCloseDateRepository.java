package tour.nonghaeng.domain.experience.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienceCloseDateRepository extends JpaRepository<ExperienceCloseDate,Long> {

    boolean existsByExperienceAndCloseDate(Experience experience, LocalDate closeDate);

    Optional<ExperienceCloseDate> findByExperienceAndCloseDate(Experience experience, LocalDate closeDate);

}
