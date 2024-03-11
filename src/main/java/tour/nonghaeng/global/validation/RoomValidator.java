package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomRepository roomRepository;

    public void pageValidate(Page<Room> roomPage) {
        if (roomPage.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_TOUR_ROOM_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }

    public void roomConditionValidate(List<RoomSummaryDto> roomSummaryDtos) {
        //방이 비어있는지 검사
        if (roomSummaryDtos.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_ROOM_AT_THIS_CONDITION);
        }
        //날짜
    }

    public void showRoomSummaryRequestParamValidate(List<Room> rooms, LocalDate requestDate, int numOfRoom) {
        isEmptyRoomValidate(rooms);
        dateValidate(rooms,requestDate);
    }

    private void isEmptyRoomValidate(List<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_ROOM_IN_THIS_TOUR);
        }
    }

    private void dateValidate(List<Room> rooms, LocalDate requestDate) {
        LocalDate today = LocalDate.now();

        if (requestDate.isBefore(today)) {
            throw new RoomException(RoomErrorCode.PAST_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }

        for (Room room : rooms) {
            List<LocalDate> closeDateList = room.getRoomCloseDateList().stream().map(roomCloseDate -> roomCloseDate.getCloseDate()).toList();
            if (closeDateList.contains(requestDate)) {
                throw new RoomException(RoomErrorCode.CLOSE_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
            }
        }

    }
}
