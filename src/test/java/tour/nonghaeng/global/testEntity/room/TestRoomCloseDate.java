package tour.nonghaeng.global.testEntity.room;

import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.data.RoomCloseDate;

import java.time.LocalDate;

public class TestRoomCloseDate {

    public static RoomCloseDate makeTestRoomCloseDate(Room room,LocalDate closeDate) {
        return RoomCloseDate.builder()
                .closeDate(closeDate)
                .room(room)
                .build();
    }
}
