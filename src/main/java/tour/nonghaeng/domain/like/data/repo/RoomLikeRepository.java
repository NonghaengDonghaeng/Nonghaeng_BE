package tour.nonghaeng.domain.like.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.like.data.RoomLike;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.room.data.Room;

import java.util.Optional;

@Repository
public interface RoomLikeRepository extends JpaRepository<RoomLike, Long> {

    int countByRoom(Room room);

    Optional<RoomLike> findByUserAndRoom(User user, Room room);
}
