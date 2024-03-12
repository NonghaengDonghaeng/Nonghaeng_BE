package tour.nonghaeng.domain.experience.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienceCloseDateRepository extends JpaRepository<ExperienceCloseDate,Long> {

    boolean existsByExperienceAndCloseDate(Experience experience, LocalDate closeDate);

    Optional<ExperienceCloseDate> findByExperienceAndCloseDate(Experience experience, LocalDate closeDate);

}
