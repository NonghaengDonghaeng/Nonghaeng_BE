package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationResponseDto;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.reservation.ReservationValidator;
import tour.nonghaeng.global.validation.reservation.RoomReservationValidator;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;

    private final RoomService roomService;
    private final UserService userService;

    private final RoomReservationValidator roomReservationValidator;
    private final ReservationValidator reservationValidator;


    public RoomReservationResponseDto createRoomReservation(User user, CreateRoomReservationDto requestDto) {

        Room room = roomService.findById(requestDto.getRoomId());

        roomReservationValidator.roomReservationValidate(room, user, requestDto);

        userService.payPoint(user, requestDto.getFinalPrice());

        RoomReservation roomReservation = roomReservationRepository.save(requestDto.toEntity(user, room));

        return RoomReservationResponseDto.toDto(roomReservation);
    }

    //해당 날짜의 남은 방 수 구하기
    public int countRemainOfRoom(Room room, LocalDate date) {

        int currentReservationRoom = roomReservationRepository.countByRoomAndReservationDate(room, date)
                .orElse(0);

        return room.getNumOfRoom() - currentReservationRoom;
    }

    private RoomReservation findById(Long roomReservationId) {

        return roomReservationRepository.findById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID));
    }

    public Reservation findReservationById(Long reservationId) {
        return roomReservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID));
    }

    public Page<Reservation> findReservationPageByUser(User user, Pageable pageable) {
        return roomReservationRepository.findReservationAllByUser(user, pageable);
    }

    public Page<Reservation> findReservationPageBySeller(Seller seller, Pageable pageable) {
        return roomReservationRepository.findReservationAllBySeller(seller, pageable);
    }

    public LocalDate findStartDateById(Long roomReservationId) {
        return roomReservationRepository.findStartDateById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_RESERVATION_DATE_BY_ID));
    }
}
