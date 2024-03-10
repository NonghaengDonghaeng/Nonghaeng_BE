package tour.nonghaeng.domain.room.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
