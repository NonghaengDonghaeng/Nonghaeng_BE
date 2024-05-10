package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.entity.ExperienceLike;

@Repository
public interface ExperienceLikeRepository extends JpaRepository<ExperienceLike, Long> {

    @Query("select count(el) from ExperienceLike el where el.user.id = :userId and el.experience.id = :experienceId")
    int countByExperienceIdAndUserId(@Param("userId") Long userId,@Param("experienceId") Long experienceId);

}
