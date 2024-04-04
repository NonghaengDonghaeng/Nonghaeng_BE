package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.SellerService;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.repo.ReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.reservation.ReservationValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ExperienceReservationService experienceReservationService;
    private final RoomReservationService roomReservationService;
    private final UserService userService;
    private final SellerService sellerService;

    private final ReservationValidator reservationValidator;


    public RoomReservationResponseDto createRoomReservation(User user, CreateRoomReservationDto reservationDto) {
        return roomReservationService.createRoomReservation(user, reservationDto);
    }

    public ExpReservationResponseDto createExpReservation(User user, CreateExpReservationDto requestDto) {
        return experienceReservationService.createExpReservation(user, requestDto);
    }

    public Page<? extends ReservationUserSummaryDto> getReservationUserSummaryDtoPage(User user, Pageable pageable,String type) {

        Page<Reservation> reservationPage = findReservationPageByUser(user, pageable, type);

        reservationValidator.pageValidate(reservationPage);
        //여기서 toDto 는 오버라이딩된 toDto 함수 사용됨
        Page<? extends ReservationUserSummaryDto> dtoPage = reservationPage.map(reservation -> reservation.toUserSummaryDto());

        return dtoPage;
    }

    public ReservationUserDetailDto getReservationUserDetailDto(Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findByReservationId(reservationId);

        return reservation.toUserDetailDto();
    }

    public Page<? extends ReservationSellerSummaryDto> getReservationSellerSummaryDtoPage(Seller seller, Pageable pageable,String type) {

        Page<Reservation> reservationPage = findReservationPageBySeller(seller, pageable, type);

        reservationValidator.pageValidate(reservationPage);

        Page<? extends ReservationSellerSummaryDto> dtoPage = reservationPage.map(reservation -> reservation.toSellerSummaryDto());

        return dtoPage;
    }

    public ReservationSellerDetailDto getReservationSellerDetailDto(Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findByReservationId(reservationId);

        int remainOfParticipant = 0;

        if (reservation instanceof ExperienceReservation) {
            ExperienceReservation experienceReservation = (ExperienceReservation) reservation;
            remainOfParticipant = experienceReservationService.countRemainOfParticipant(experienceReservation.getExperienceRound(), experienceReservation.getReservationDate());
        }

        return reservation.toSellerDetailDto(remainOfParticipant);
    }

    public Long approveReservation(Long reservationId, boolean notApproveFlag) {

        Reservation reservation = findByReservationId(reservationId);

        reservationValidator.checkWaitingState(reservation);

        if (notApproveFlag) {
            reservation.notApproveReservation();
            userService.payBackPoint(reservation.getUser(), reservation.getPrice(),
                    CancelPolicy.NOT_CONFIRM_CANCEL_POLICY);
        }
        reservation.approveReservation();

        return reservationRepository.save(reservation).getId();
    }

    public ReservationCancelResponseDto cancelReservation(User user, Long reservationId) {

        Reservation reservation = findByReservationId(reservationId);

        reservationValidator.checkCancelState(reservation);

        CancelPolicy cancelPolicy = decideCancelPolicy(reservation);

        userService.payBackPoint(user, reservation.getPrice(), cancelPolicy);

        reservation.cancelReservation();

        reservationRepository.save(reservation);

        return reservation.toCancelResponseDto(cancelPolicy);
    }

    //다운캐스팅안해도 될것같애서 안하고 이 구현채를 넘겨서 확인해보기
    private Page<? extends ReservationUserSummaryDto> downCastingUserSummaryDto(Page<ReservationUserSummaryDto> dtoPage) {

        List<? extends ReservationUserSummaryDto> filteredList = dtoPage.getContent().stream()
                .map(dto->{
                    if(dto instanceof RoomReservationUserSummaryDto){
                        return (RoomReservationUserSummaryDto) dto;
                    }else{
                        return (ExpReservationUserSummaryDto) dto;
                    }
                })
                .toList();

        return new PageImpl<>(filteredList, dtoPage.getPageable(), dtoPage.getTotalElements());
    }

    private Page<? extends ReservationSellerSummaryDto> downCastingSellerSummaryDto(Page<ReservationSellerSummaryDto> dtoPage) {

        List<? extends ReservationSellerSummaryDto> filteredList = dtoPage.getContent().stream()
                .map(dto -> {
                    if (dto instanceof RoomReservationSellerSummaryDto) {
                        return (RoomReservationSellerSummaryDto) dto;
                    } else {
                        return (ExpReservationSellerSummaryDto) dto;
                    }
                })
                .toList();

        return new PageImpl<>(filteredList, dtoPage.getPageable(), dtoPage.getTotalElements());
    }

    private Page<Reservation> findReservationPageByUser(User user, Pageable pageable,String type) {

        if (type.equals("room")) {

            return roomReservationService.findReservationPageByUser(user, pageable);
        }
        return experienceReservationService.findReservationPageByUser(user, pageable);
    }

    private Page<Reservation> findReservationPageBySeller(Seller seller, Pageable pageable, String type) {

        if (type.equals("room")) {

            return roomReservationService.findReservationPageBySeller(seller, pageable);
        }
        return experienceReservationService.findReservationPageBySeller(seller, pageable);
    }

    private Reservation findByReservationId(Long reservationId) {

        String reservationType = reservationRepository.findReservationType(reservationId);

        if (reservationType.equals("RoomReservation")) {
            return roomReservationService.findReservationById(reservationId);
        }

        return experienceReservationService.findReservationById(reservationId);
    }

    private CancelPolicy decideCancelPolicy(Reservation reservation) {
        LocalDate reservationAt = reservation.getCreatedAt().toLocalDate();
        LocalDateTime startAt = findStartAt(reservation);

        Long diffHour = countDiffHourDate(startAt);

        if (reservation.getStateType().equals(ReservationStateType.WAITING_RESERVATION)) {
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

    private Long countDiffHourDate(LocalDateTime startAt) {

        Duration duration = Duration.between(LocalDateTime.now(), startAt);

        return duration.getSeconds() / 3600;
    }

    private LocalDateTime findStartAt(Reservation reservation) {
        if (reservation instanceof RoomReservation) {
            RoomReservation roomReservation = (RoomReservation) reservation;
            LocalDateTime.of(roomReservationService.findStartDateById(roomReservation.getId()),
                    roomReservation.getRoom().getCheckinTime());
        }
        ExperienceReservation experienceReservation = (ExperienceReservation) reservation;
        return LocalDateTime
                .of(experienceReservation.getReservationDate(), experienceReservation.getExperienceRound().getStartTime());
    }

    public Reservation findById(Long reservationId) {

        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_RESERVATION_ID));
    }

    public void autoChangeCompleteReservation() {
        List<Reservation> allConfirmReservation = reservationRepository.findAllConfirmReservation();

        allConfirmReservation.forEach(reservation -> {
            completeReservation(reservation);
        });
    }

    private void completeReservation(Reservation reservation) {

        if (checkPastReservation(reservation)) {

            reservation.changeCompleteReservation();
            sellerService.payBackPoint(reservation.getSeller(), reservation.getPrice());
            reservationRepository.save(reservation);
        }
    }

    private boolean checkPastReservation(Reservation reservation) {

        if (reservation.getStateType().equals("room")) {

            return checkPast(roomReservationService.findEndDateById(reservation.getId()));
        }
        return checkPast(experienceReservationService.findEndDateById(reservation.getId()));
    }

    private boolean checkPast(LocalDate reservationDate) {

        if (reservationDate.isAfter(LocalDate.now())) {
            return true;
        }
        return false;
    }

}
