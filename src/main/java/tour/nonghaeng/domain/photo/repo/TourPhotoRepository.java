package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.TourPhoto;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourPhotoRepository extends JpaRepository<TourPhoto, Long> {

    boolean existsById(Long tourPhotoId);

    @Query("select count(tp) from TourPhoto tp where tp.tour = :tour and tp.representative = true")
    Integer countRepresentative(@Param("tour") Tour tour);

    @Query("select count(tp) = 1 from TourPhoto tp where tp.tour = :tour and tp.representative=true")
    boolean hasExactlyOneRepresentativePhoto(@Param("tour") Tour tour);

    @Query("select tp.id from TourPhoto tp where tp.tour = :tour and tp.representative=true")
    Optional<Long> findRepresentativePhotoId(@Param("tour") Tour tour);

    @Query("select tp from TourPhoto tp where tp.tour = :tour")
    List<Photo> findAllByTour(@Param("tour") Tour tour);

}
