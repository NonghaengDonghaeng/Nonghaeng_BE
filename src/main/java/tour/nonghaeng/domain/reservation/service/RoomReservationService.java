package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.room.*;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.reservation.RoomReservationValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;

    private final RoomService roomService;
    private final UserService userService;

    private final RoomReservationValidator roomReservationValidator;


    public RoomReservationResponseDto createRoomReservation(User user, CreateRoomReservationDto requestDto) {

        Room room = roomService.findById(requestDto.getRoomId());

        roomReservationValidator.roomReservationValidate(room, user, requestDto);

        userService.payPoint(user, requestDto.getFinalPrice());

        RoomReservation roomReservation = roomReservationRepository.save(requestDto.toEntity(user, room));

        return RoomReservationResponseDto.toDto(roomReservation);
    }

    public Page<RoomReservationUserSummaryDto> getRoomReservationUserSummaryDtoPage(User user, Pageable pageable) {

        Page<RoomReservation> page = roomReservationRepository.findAllByUser(user, pageable);

        roomReservationValidator.pageValidate(page);

        return RoomReservationUserSummaryDto.toPageDto(page);
    }

    public Page<RoomReservationSellerSummaryDto> getRoomReservationSellerSummaryDtoPage(Seller seller, Pageable pageable) {

        Page<RoomReservation> page = roomReservationRepository.findAllBySeller(seller, pageable);

        roomReservationValidator.pageValidate(page);

        return RoomReservationSellerSummaryDto.toPageDto(page);
    }

    public RoomReservationUserDetailDto getRoomReservationUserDetailDto(Long roomReservationId) {

        RoomReservation roomReservation = findById(roomReservationId);

        return RoomReservationUserDetailDto.toDto(roomReservation);
    }

    public RoomReservationSellerDetailDto getRoomReservationSellerDetailDto(Long roomReservationId) {

        RoomReservation roomReservation = findById(roomReservationId);

        return RoomReservationSellerDetailDto.toDto(roomReservation);
    }

    public Long approveRoomReservation(Long roomReservationId,boolean notApproveFlag) {

        RoomReservation roomReservation = findById(roomReservationId);

        roomReservationValidator.checkWaitingState(roomReservation);

        if (notApproveFlag) {
            roomReservation.notApproveReservation();
            userService.payBackPoint(roomReservation.getUser(), roomReservation.getPrice(), CancelPolicy.NOT_CONFIRM_CANCEL_POLICY);
        }
        roomReservation.approveReservation();

        return roomReservationRepository.save(roomReservation).getId();
    }

    public RoomReservationCancelResponseDto cancelRoomReservation(User user, Long roomReservationId) {

        RoomReservation roomReservation = findById(roomReservationId);

        roomReservationValidator.checkCancelState(roomReservation);

        CancelPolicy cancelPolicy = decideCancelPolicy(roomReservation);

        userService.payBackPoint(user, roomReservation.getPrice(), cancelPolicy);

        roomReservation.cancelReservation();

        roomReservationRepository.save(roomReservation);

        return RoomReservationCancelResponseDto.toDto(roomReservation, cancelPolicy);
    }

    //해당 날짜의 남은 방 수 구하기
    public int countRemainOfRoom(Room room, LocalDate date) {

        int currentReservationRoom = roomReservationRepository.countByRoomAndReservationDate(room, date)
                .orElse(0);

        return room.getNumOfRoom() - currentReservationRoom;
    }

    private Long countDiffHourDate(LocalDateTime startAt) {

        Duration duration = Duration.between(LocalDateTime.now(), startAt);

        return duration.getSeconds() / 3600;
    }

    private CancelPolicy decideCancelPolicy(RoomReservation roomReservation) {

        LocalDate reservationAt = roomReservation.getCreatedAt().toLocalDate();
        LocalDateTime roomStartAt = LocalDateTime.of(findStartDateById(roomReservation.getId()),
                roomReservation.getRoom().getCheckinTime());

        Long diffHour = countDiffHourDate(roomStartAt);

        if (roomReservation.getStateType().equals(ReservationStateType.WAITING_RESERVATION)) {
            return CancelPolicy.NOT_CONFIRM_CANCEL_POLICY;
        }
        if (reservationAt.equals(LocalDate.now())) {
            return CancelPolicy.MISTAKE_CANCEL_POLICY;
        }
        if (diffHour < 7) {
            return CancelPolicy.IN_SEVEN_HOURS_CANCEL_POLICY;
        }
        if (diffHour < 24) {
            return CancelPolicy.IN_ONE_DAY_CANCEL_POLICY;
        }
        if (diffHour < 24 * 7) {
            return CancelPolicy.IN_ONE_WEEK_CANCEL_POLICY;
        }
        return CancelPolicy.DEFAULT_CANCEL_POLICY;
    }

    private RoomReservation findById(Long roomReservationId) {

        return roomReservationRepository.findById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID));
    }

    private LocalDate findStartDateById(Long roomReservationId) {
        return roomReservationRepository.findStartDateById(roomReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_RESERVATION_DATE_BY_ID));
    }
}
