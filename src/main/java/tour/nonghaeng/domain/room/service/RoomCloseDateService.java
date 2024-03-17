package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.room.dto.AddRoomCloseDateDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;
import tour.nonghaeng.domain.room.repo.RoomCloseDateRepository;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.RoomErrorCode;
import tour.nonghaeng.global.validation.room.RoomCloseDateValidator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomCloseDateService {

    private final RoomCloseDateRepository roomCloseDateRepository;

    private final RoomCloseDateValidator roomCloseDateValidator;


    public void addCloseDates(Room room, List<AddRoomCloseDateDto> roomCloseDateDtos) {

        roomCloseDateValidator.addCLoseDateDtoValidate(roomCloseDateDtos);

        for (AddRoomCloseDateDto dto : roomCloseDateDtos) {
            room.addCloseDate(createAndSave(room,dto));
        }
    }

    public RoomCloseDate findByRoomAndCloseDate(Room room, LocalDate closeDate) {

        return roomCloseDateRepository.findByRoomAndCloseDate(room, closeDate)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NOT_EXIST_ROOM_CLOSE_DATE_ERROR));
    }

    private RoomCloseDate createAndSave(Room room, AddRoomCloseDateDto roomCloseDateDto) {

        roomCloseDateValidator.createAndSaveValidate(room, roomCloseDateDto);

        return roomCloseDateRepository.save(roomCloseDateDto.toEntity(room));
    }
}
