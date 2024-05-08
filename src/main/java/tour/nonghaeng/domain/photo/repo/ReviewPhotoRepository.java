package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.ReviewPhoto;
import tour.nonghaeng.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {

    boolean existsById(Long reviewPhotoId);

    @Query("select count(rp) from ReviewPhoto rp where rp.review = :review and rp.representative=true")
    Integer countRepresentative(@Param("review") Review review);

    @Query("select count(rp) = 1 from ReviewPhoto rp where rp.review = :review and rp.representative=true")
    boolean hasExactlyOneRepresentativePhoto(@Param("review") Review review);

    @Query("select rp.id from ReviewPhoto rp where rp.review = :review and rp.representative = true")
    Optional<Long> findRepresentativePhotoId(@Param("review") Review review);

    @Query("select rp from ReviewPhoto rp where rp.review = :review")
    List<Photo> findAllByReview(@Param("review") Review review);

    @Query("select rp from ReviewPhoto rp where rp.id = :id")
    Optional<Photo> findPhotoById(@Param("id") Long id);
}
