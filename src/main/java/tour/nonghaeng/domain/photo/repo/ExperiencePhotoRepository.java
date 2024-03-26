package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.ExperiencePhoto;

@Repository
public interface ExperiencePhotoRepository extends JpaRepository<ExperiencePhoto, Long> {

    boolean existsById(Long experiencePhotoId);

}
