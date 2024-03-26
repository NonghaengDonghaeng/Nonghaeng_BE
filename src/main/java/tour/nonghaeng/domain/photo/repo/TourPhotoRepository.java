package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.TourPhoto;

@Repository
public interface TourPhotoRepository extends JpaRepository<TourPhoto, Long> {

    boolean existsById(Long tourPhotoId);
}
