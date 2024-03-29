package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.room.RoomCloseDateValidator;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomReservationValidator {

    private final RoomReservationRepository roomReservationRepository;

    private final RoomCloseDateValidator roomCloseDateValidator;
    private final ReservationValidator reservationValidator;


    public void roomReservationValidate(Room room, User user, CreateRoomReservationDto dto) {

        createRoomReservationDtoValidate(room,dto);

        reservationValidator.checkPointValidate(user,dto.getFinalPrice());
    }

    //TODO: 가격구하는 함수 따로 만들기
    public void createRoomReservationDtoValidate(Room room, CreateRoomReservationDto dto) {

        //최대인원확인
        if (dto.getNumOfParticipant() > room.getMaxCapacity() * dto.getNumOfRoom()) {
            throw new ReservationException(ReservationErrorCode.EXCEEDED_MAX_CAPACITY);
        }

        //가격확인
        int standardParticipant = room.getStandardCapacity() * dto.getNumOfRoom();
        int additionalCost = room.getAdditionalCost();
        int additionNum = dto.getNumOfParticipant() - standardParticipant;
        if (additionNum < 0) {
            additionNum = 0;
        }
        //TODO: 성수기,공휴일에 따라 변하기
        int price = room.getPriceOffPeak();

        if (dto.getFinalPrice() != (price * dto.getNumOfRoom()+ (additionalCost * additionNum))  * dto.getReservationDates().size()) {
            throw new ReservationException(ReservationErrorCode.WRONG_FINAL_PRICE_ERROR);
        }
        //운영날짜 확인
        dto.getReservationDates().forEach(date -> roomCloseDateValidator.isOpenDateParameterValidate(room, date));

        //객실 수 확인
        dto.getReservationDates().forEach(localDate -> checkRemainRoomByDate(room,dto.getNumOfRoom(),localDate));

    }

    private void checkRemainRoomByDate(Room room, int numOfRoom, LocalDate reservationDate) {

        Integer currentNum = roomReservationRepository.countByRoomAndReservationDate(room, reservationDate)
                .orElse(0);

        if (numOfRoom > room.getNumOfRoom() - currentNum) {
            throw new ReservationException(ReservationErrorCode.EXCEEDED_NUM_OF_ROOM);
        }
    }
}
