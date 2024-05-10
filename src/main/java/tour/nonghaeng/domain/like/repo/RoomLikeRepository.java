package tour.nonghaeng.domain.like.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.entity.RoomLike;

@Repository
public interface RoomLikeRepository extends JpaRepository<RoomLike, Long> {

    @Query("select count(rl) from RoomLike rl where rl.user.id = :userId and rl.room.id = :roomId")
    int countByRoomIdAndUserId(@Param("userId") Long userId, @Param("roomId") Long roomId);
}
