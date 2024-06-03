package tour.nonghaeng.domain.like.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.like.data.ExperienceLike;
import tour.nonghaeng.domain.member.data.User;

import java.util.Optional;

@Repository
public interface ExperienceLikeRepository extends JpaRepository<ExperienceLike, Long> {


    int countByExperience(Experience experience);

    Optional<ExperienceLike> findByUserAndExperience(User user, Experience experience);
}
