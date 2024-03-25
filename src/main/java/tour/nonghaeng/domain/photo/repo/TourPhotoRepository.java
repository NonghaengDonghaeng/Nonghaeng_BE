package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.TourPhoto;

import java.util.Optional;

@Repository
public interface TourPhotoRepository extends JpaRepository<TourPhoto, Long> {

    @Query("select tp.imgUrl from TourPhoto tp where tp.id= :id")
    Optional<String> findUrlById(@Param("id") Long tourPhotoId);

    boolean existsById(Long tourPhotoId);
}
