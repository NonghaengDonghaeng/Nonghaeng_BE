package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.SellerService;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationResponseDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.exception.ReservationException;
import tour.nonghaeng.domain.reservation.exception.error.ReservationErrorCode;
import tour.nonghaeng.domain.reservation.repo.ReservationRepository;
import tour.nonghaeng.domain.reservation.valid.ReservationValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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




    public Reservation findById(Long reservationId) {

        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_RESERVATION_ID));
    }



    //예약 생성 서비스
    public RoomReservationResponseDto createRoomReservation(Member user, CreateRoomReservationDto reservationDto) {

        return roomReservationService.createRoomReservation(user, reservationDto);
    }



    public ExpReservationResponseDto createExpReservation(Member user, CreateExpReservationDto requestDto) {

        return experienceReservationService.createExpReservation(user, requestDto);
    }



    //예약조회 서비스
    public ReservationUserDetailDto getReservationUserDetailDto(Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findUpCastedReservationById(reservationId);

        return reservation.toUserDetailDto();
    }



    public ReservationSellerDetailDto getReservationSellerDetailDto(Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findUpCastedReservationById(reservationId);

        return reservation.toSellerDetailDto(countRemainOfParticipant(reservation));
    }

    private int countRemainOfParticipant(Reservation reservation) {

        if(reservation instanceof ExperienceReservation expReservation) {

            return experienceReservationService.countRemainOfParticipant(expReservation.getExperienceRound(),
                    expReservation.getReservationDate());
        }
        return 0;
    }



    public Page<? extends ReservationUserSummaryDto> getReservationUserSummaryDtoPage(Member user, Pageable pageable,String type) {

        Page<Reservation> reservationPage = getReservationPageByUserAndType(user, pageable, type);

        if(!reservationPage.hasContent()) {
            return Page.empty();
        }

        return reservationPage.map(Reservation::toUserSummaryDto);
    }

    private Page<Reservation> getReservationPageByUserAndType(Member user, Pageable pageable, String type) {

        if (type.equals("room")) {

            return roomReservationService.findReservationPageByUser(user, pageable);
        } else if (type.equals("experience")) {

            return experienceReservationService.findReservationPageByUser(user, pageable);
        }
        // type: all 일때
        return reservationRepository.findReservationPageByUser((User) user, pageable)
                .map(reservation -> findUpCastedReservationById(reservation.getId()));
    }


    public Page<? extends ReservationSellerSummaryDto> getReservationSellerSummaryDtoPage(Member seller, Pageable pageable,String type) {

        Page<Reservation> reservationPage = findReservationPageBySeller(seller, pageable, type);

        reservationValidator.pageValidate(reservationPage);

        return reservationPage.map(Reservation::toSellerSummaryDto);
    }

    private Page<Reservation> findReservationPageBySeller(Member seller, Pageable pageable, String type) {

        if (type.equals("room")) {

            return roomReservationService.findReservationPageBySeller(seller, pageable);
        }
        else if (type.equals("exp")) {

            return experienceReservationService.findReservationPageBySeller(seller, pageable);
        }
        return findReservationPageBySeller(seller, pageable);
    }

    private Page<Reservation> findReservationPageBySeller(Member seller, Pageable pageable) {

        return reservationRepository.findReservationPageBySeller((Seller) seller, pageable)
                .map(reservation -> findUpCastedReservationById(reservation.getId()));
    }


    private Reservation findUpCastedReservationById(Long reservationId) {

        String dtype = reservationRepository.findReservationTypeById(reservationId);

        //TODO: dtype 검증추가

        if (dtype.equals("room")) {

            return roomReservationService.findReservationById(reservationId);
        }

        return experienceReservationService.findReservationById(reservationId);
    }



    public ReservationPersonInfo getReservationPersonInfo(Member user) {
        return ReservationPersonInfo.toDto(user);
    }



    //취소,승인 서비스
    public Long approveReservation(Long reservationId, boolean notApproveFlag) {

        Reservation reservation = findUpCastedReservationById(reservationId);

        reservationValidator.checkWaitingState(reservation);

        if (notApproveFlag) {
            notApproveReservation(reservation);
        }

        reservation.approveReservation();

        return reservationRepository.save(reservation).getId();
    }

    private void notApproveReservation(Reservation reservation) {

        reservation.notApproveReservation();

        userService.payBackPoint(reservation.getUser(), reservation.getPrice(),
                CancelPolicy.NOT_CONFIRM_CANCEL_POLICY);
    }



    public ReservationCancelResponseDto cancelReservation(Member user, Long reservationId) {

        Reservation reservation = findUpCastedReservationById(reservationId);

        reservationValidator.checkCancelState(reservation);

        CancelPolicy cancelPolicy = decideCancelPolicy(reservation);

        userService.payBackPoint((User) user, reservation.getPrice(), cancelPolicy);

        reservation.cancelReservation();

        reservationRepository.save(reservation);

        return reservation.toCancelResponseDto(cancelPolicy);
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

        if (reservation instanceof RoomReservation roomReservation) {

            return LocalDateTime.of(roomReservationService.findStartDateById(roomReservation.getId()),
                    roomReservation.getRoom().getCheckinTime());
        }

        assert reservation instanceof ExperienceReservation;
        ExperienceReservation experienceReservation = (ExperienceReservation) reservation;

        return LocalDateTime
                .of(experienceReservation.getReservationDate(), experienceReservation.getExperienceRound().getStartTime());
    }



    //스케줄링 서비스
    public void autoChangeCompleteReservation() {

        reservationRepository.findAllConfirmReservation()
                .ifPresent(
                        reservations -> reservations.forEach(this::autoCompleteReservation)
                );
    }

    private void autoCompleteReservation(Reservation reservation) {

        if (checkPastReservation(reservation)) {

            reservation.changeCompleteReservation();
            sellerService.payBackPoint(reservation.getSeller(), reservation.getPrice());
            reservationRepository.save(reservation);
        }
    }



    public void autoChangeCancelReservation() {

        reservationRepository.findAllWaitingReservation()
                .ifPresent(
                        reservations -> reservations.forEach(this::autoCancelReservation)
                );
    }

    private void autoCancelReservation(Reservation reservation) {
        if (checkPastReservation(reservation)) {
            reservation.cancelReservation();
            userService.payBackPoint(reservation.getUser(), reservation.getPrice(), CancelPolicy.NOT_CONFIRM_CANCEL_POLICY);
            reservationRepository.save(reservation);
        }
    }


    private boolean checkPastReservation(Reservation reservation) {

        String dtype = reservationRepository.findReservationTypeById(reservation.getId());

        if (dtype.equals("room")) {

            return checkPast(roomReservationService.findEndDateById(reservation.getId()));
        } else if (dtype.equals("experience")) {

            return checkPast(experienceReservationService.findEndDateById(reservation.getId()));
        }
        throw new ReservationException(ReservationErrorCode.DEFAULT_RESERVATION_ERROR);
    }

    private boolean checkPast(LocalDate reservationDate) {

        if (reservationDate.isAfter(LocalDate.now())) {
            return true;
        }
        return false;
    }


    public String findTypeById(Long reservationId) {
        return reservationRepository.findReservationTypeById(reservationId);
    }

}
