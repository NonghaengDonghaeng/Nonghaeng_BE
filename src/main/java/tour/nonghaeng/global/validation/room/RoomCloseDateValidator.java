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


    //TODO: 함수이름 리팩토링해야됨 CL -> Cl
    public void addCLoseDateDtoValidate(List<AddRoomCloseDateDto> addRoomCloseDateDtos) {

        LocalDate today = LocalDate.now();

        if (addRoomCloseDateDtos.stream()
                .anyMatch(dto -> dto.getCloseDate().isBefore(today))) {
            throw new RoomException(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR);
        }

        //중복처리를 위한 AddRoomCloseDateDto 클래스에 equals() 함수 재정의 - 날짜가 같으면 같도록
        //equals() 기본은 메모리 주소를 봐서 다른 객체면 다르게 처리됨.
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

    public void isOpenDateParameterValidate(Room room, LocalDate dateParam) {

        //오늘 이후인지
        if (dateParam.isBefore(LocalDate.now())) {
            throw new RoomException(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR);
        }
        //운영종료 리스트에 들어가있는지
        if (isExistCloseDateValidate(room, dateParam)) {
            throw new RoomException(RoomErrorCode.NOT_RUNNING_PERIOD_ERROR);
        }
    }

    private boolean isExistCloseDateValidate(Room room, LocalDate date) {

        if (roomCloseDateRepository.existsByRoomAndCloseDate(room, date)) {
            return true;
        }
        return false;
    }

}
