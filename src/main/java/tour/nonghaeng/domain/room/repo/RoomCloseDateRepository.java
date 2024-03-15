package tour.nonghaeng.domain.room.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;

import java.time.LocalDate;

@Repository
public interface RoomCloseDateRepository extends JpaRepository<RoomCloseDate,Long> {

    boolean existsByRoomAndCloseDate(Room room, LocalDate closeDate);

}
