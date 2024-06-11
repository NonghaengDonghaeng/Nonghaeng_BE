package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.global.infra.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.data.RoomReservation;
import tour.nonghaeng.domain.reservation.data.repo.RoomReservationRepository;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.presentation.exception.ReservationException;
import tour.nonghaeng.domain.reservation.presentation.exception.error.ReservationErrorCode;
import tour.nonghaeng.domain.reservation.service.valid.RoomReservationValidator;
import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.auth.AuthValidator;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomReservationService implements SubReservationService<RoomReservation,Room,CreateRoomReservationDto> {

    private final RoomReservationRepository roomReservationRepository;

    private final RoomService roomService;
    private final UserService userService;

    private final RoomReservationValidator roomReservationValidator;
    private final AuthValidator authValidator;


    @Override
    public ReservationServiceType getType() {
        return ReservationServiceType.ROOM;
    }


    @Override
    public RoomReservation findById(Long roomReservationId) {

        return roomReservationRepository.findById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID));
    }


    @Override
    public RoomReservation create(Member member, CreateRoomReservationDto createDto) {

        createDto.toSetLocalDateList();

        User user = authValidator.userValidate(member);

        Room room = roomService.findById(createDto.getRoomId());

        roomReservationValidator.roomReservationValidate(room, member, createDto);

        userService.payPoint(user, createDto.getFinalPrice());

        return roomReservationRepository.save(createDto.toEntity(user, room));
    }




    @Override
    public Page<? extends ReservationSummaryDto> getReservationSummaryDtoPage(Member member, Pageable pageable) {

        if (member instanceof Seller) {
            Page<Reservation> reservationPage = roomReservationRepository.findReservationPageBySeller(authValidator.sellerValidate(member), pageable);

            if (!reservationPage.hasContent()) {
                return Page.empty();
            }
            return reservationPage.map(Reservation::toSummaryDtoForSeller);
        }
        Page<Reservation> reservationPage = roomReservationRepository.findReservationPageByUser(authValidator.userValidate(member), pageable);
        if (!reservationPage.hasContent()) {
            return Page.empty();
        }
        return reservationPage.map(Reservation::toSummaryDto);
    }


    @Override
    public LocalDate findStartDateById(Long roomReservationId) {
        return roomReservationRepository.findStartDateById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_RESERVATION_DATE_BY_ID));
    }


    @Override
    public LocalDate findEndDateById(Long roomReservationId) {
        return roomReservationRepository.findEndDateById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_RESERVATION_DATE_BY_ID));
    }


    @Override
    public int countRemain(Room room, LocalDate date) {

        int currentReservationRoom = roomReservationRepository.countByRoomAndReservationDate(room, date)
                .orElse(0);

        return room.getNumOfRoom() - currentReservationRoom;
    }
}
