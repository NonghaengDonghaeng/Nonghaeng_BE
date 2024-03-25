package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.RoomPhoto;

@Repository
public interface RoomPhotoRepository extends JpaRepository<RoomPhoto, Long> {

    boolean existsById(Long tourPhotoId);
}
