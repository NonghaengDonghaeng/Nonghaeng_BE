package tour.nonghaeng.domain.experience.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Seller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    @Query("SELECT e.seller FROM Experience e WHERE e.id = :experienceId")
    Optional<Seller> findSellerByExperienceId(@Param("experienceId") Long experienceId);

    boolean existsById(Long experienceId);

    @Query("SELECT MIN(ecd.closeDate) FROM ExperienceCloseDate ecd where ecd.experience.id = :id")
    Optional<LocalDate> findOldestCloseDate(@Param("id") Long experienceId);

    @Query("select e.id from Experience e")
    List<Long> findAllIds();

    Page<Experience> findAll(Pageable pageable);

}
