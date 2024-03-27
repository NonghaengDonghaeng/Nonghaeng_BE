package tour.nonghaeng.domain.photo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.RoomPhoto;
import tour.nonghaeng.domain.room.entity.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomPhotoRepository extends JpaRepository<RoomPhoto, Long> {

    boolean existsById(Long tourPhotoId);

    @Query("select count(rp) from RoomPhoto rp where rp.room = :room and rp.representative=true")
    Integer countRepresentative(@Param("room") Room room);

    @Query("select count(rp) = 1 from RoomPhoto rp where rp.room = :room and rp.representative=true")
    boolean hasExactlyOneRepresentativePhoto(@Param("room") Room room);

    @Query("select rp.id from RoomPhoto rp where rp.room = :room and rp.representative = true")
    Optional<Long> findRepresentativePhotoId(@Param("room") Room room);

    @Query("select rp from RoomPhoto rp where rp.room = :room")
    List<Photo> findAllByRoom(@Param("room") Room room);


}
