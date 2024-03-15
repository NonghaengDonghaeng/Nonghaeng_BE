package tour.nonghaeng.global.validation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Seller;
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


    public void ownerValidate(Seller seller, Long roomId) {

        roomIdValidate(roomId);

        if (!seller.equals(roomRepository.findSellerByRoomId(roomId).get())) {
            throw new RoomException(RoomErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void roomConditionValidate(List<RoomSummaryDto> dtoList) {

        if (dtoList.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_ROOM_AT_THIS_CONDITION);
        }
        //날짜검사
    }

    public void showRoomSummaryRequestParamValidate(List<Room> rooms, LocalDate requestDate, int numOfRoom) {

        isEmptyRoomValidate(rooms);

        pastDateValidate(requestDate);

        for (Room room : rooms) {
            openDateValidate(room,requestDate);
        }
    }

    public void getRoomDetailDtoValidate(Room room, LocalDate requestDate) {

        pastDateValidate(requestDate);

        openDateValidate(room,requestDate);
    }

    private void isEmptyRoomValidate(List<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_ROOM_IN_THIS_TOUR);
        }
    }

    private void openDateValidate(Room room, LocalDate requestDate) {

        List<LocalDate> closeDateList = room.getRoomCloseDateList().stream().map(roomCloseDate -> roomCloseDate.getCloseDate()).toList();
        if (closeDateList.contains(requestDate)) {
            throw new RoomException(RoomErrorCode.CLOSE_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }
    }

    private void pastDateValidate(LocalDate requestDate) {

        if (requestDate.isBefore(LocalDate.now())) {
            throw new RoomException(RoomErrorCode.PAST_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }
    }

    private void roomIdValidate(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new RoomException(RoomErrorCode.NO_EXIST_ROOM_BY_ROOM_ID_ERROR);
        }
    }

    public void pageValidate(Page<Room> roomPage) {

        if (roomPage.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_TOUR_ROOM_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }
}
