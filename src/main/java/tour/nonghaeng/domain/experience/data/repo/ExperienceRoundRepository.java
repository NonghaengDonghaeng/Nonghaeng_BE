package tour.nonghaeng.domain.experience.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceRound;

import java.util.List;

@Repository
public interface ExperienceRoundRepository extends JpaRepository<ExperienceRound,Long> {

    List<ExperienceRound> findAllByExperienceOrderByStartTime(Experience experience);

    boolean existsByExperience(Experience experience);
}
