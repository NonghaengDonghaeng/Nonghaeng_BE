package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.Photo;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {

    @Query("select p.representative from Photo p where p.id = :id")
    boolean isRepresentById(@Param("id") Long photoId);

    @Query(value = "SELECT dtype FROM photos where photo_id= :id", nativeQuery = true)
    Optional<String> findPhotoType(@Param("id") Long photoId);
}
