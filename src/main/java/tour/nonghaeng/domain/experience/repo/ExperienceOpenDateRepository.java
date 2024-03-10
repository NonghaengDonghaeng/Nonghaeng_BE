package tour.nonghaeng.domain.experience.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceOpenDate;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienceOpenDateRepository extends JpaRepository<ExperienceOpenDate,Long> {

    boolean existsByExperienceAndOpenDate(Experience experience, LocalDate openDate);

    Optional<ExperienceOpenDate> findByExperienceAndOpenDate(Experience experience, LocalDate openDate);

}
