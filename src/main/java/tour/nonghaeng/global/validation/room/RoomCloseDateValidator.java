package tour.nonghaeng.global.validation.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.room.dto.AddRoomCloseDateDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomCloseDateRepository;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomCloseDateValidator {

    private final RoomCloseDateRepository roomCloseDateRepository;

    public void addCLoseDateDtoValidate(List<AddRoomCloseDateDto> addRoomCloseDateDtos) {
        LocalDate today = LocalDate.now();

        if (addRoomCloseDateDtos.stream()
                .anyMatch(dto -> dto.getCloseDate().isBefore(today))) {
            throw new RoomException(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR);
        }

        if (addRoomCloseDateDtos.size() != addRoomCloseDateDtos.stream().distinct().count()) {
            throw new RoomException(RoomErrorCode.DUPLICATE_ROOM_CLOSE_DATE_ADD_ERROR);
        }
    }

    public void createAndSaveValidate(Room room, AddRoomCloseDateDto addRoomCloseDateDto) {
        LocalDate closeDate = addRoomCloseDateDto.getCloseDate();

        if (isExistCloseDateValidate(room, closeDate)) {
            throw new RoomException(RoomErrorCode.ALREADY_EXIST_ROOM_CLOSE_DATE_ADD_ERROR);
        }
    }

    private boolean isExistCloseDateValidate(Room room, LocalDate date) {
        if (roomCloseDateRepository.existsByRoomAndCloseDate(room, date)) {
            return true;
        }
        return false;
    }

}
