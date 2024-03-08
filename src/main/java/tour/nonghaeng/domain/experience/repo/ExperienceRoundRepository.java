package tour.nonghaeng.domain.experience.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;

@Repository
public interface ExperienceRoundRepository extends JpaRepository<ExperienceRound,Long> {
}
