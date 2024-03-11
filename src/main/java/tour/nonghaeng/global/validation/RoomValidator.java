package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomRepository roomRepository;

    public void pageValidate(Page<Tour> tourPage) {
        if (tourPage.isEmpty()) {
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

    public void isEmptyRoomValidate(List<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_ROOM_IN_THIS_TOUR);
        }
    }
}
