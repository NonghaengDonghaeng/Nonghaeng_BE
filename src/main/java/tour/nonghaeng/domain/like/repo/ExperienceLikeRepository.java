package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.like.entity.ExperienceLike;
import tour.nonghaeng.domain.member.entity.User;

import java.util.Optional;

@Repository
public interface ExperienceLikeRepository extends JpaRepository<ExperienceLike, Long> {


    int countByExperience(Experience experience);

    Optional<ExperienceLike> findByUserAndExperience(User user, Experience experience);
}
