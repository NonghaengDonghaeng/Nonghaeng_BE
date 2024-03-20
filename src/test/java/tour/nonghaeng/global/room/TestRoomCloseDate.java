package tour.nonghaeng.global.room;

import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;

import java.time.LocalDate;

public class TestRoomCloseDate {

    public static final int ROOM_CLOSE_DATE_YEAR = 2024;
    public static final int ROOM_CLOSE_DATE_MONTH = 5;
    public static final int ROOM_CLOSE_DATE_DAY = 5;

    public static int num = 0;


    public static RoomCloseDate makeTestRoomCloseDate(Room room) {
        return RoomCloseDate.builder()
                .closeDate(LocalDate.of(ROOM_CLOSE_DATE_YEAR, ROOM_CLOSE_DATE_MONTH, ROOM_CLOSE_DATE_DAY + num++))
                .room(room)
                .build();
    }
}
