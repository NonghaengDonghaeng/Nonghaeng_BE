package tour.nonghaeng.domain.experience.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Seller;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    Optional<Experience> findBySeller(Seller seller);

    @Query("SELECT e.seller FROM Experience e WHERE e.id = :experienceId")
    Optional<Seller> findSellerByExperienceId(@Param("experienceId") Long experienceId);

    boolean existsById(Long experienceId);

}
