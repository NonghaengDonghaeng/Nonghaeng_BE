package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.service.SellerService;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.data.ExperienceReservation;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.data.RoomReservation;
import tour.nonghaeng.domain.reservation.data.repo.ReservationRepository;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.presentation.exception.ReservationException;
import tour.nonghaeng.domain.reservation.presentation.exception.error.ReservationErrorCode;
import tour.nonghaeng.domain.reservation.service.registry.SubReservationServiceRegistry;
import tour.nonghaeng.domain.reservation.service.valid.ReservationValidator;
import tour.nonghaeng.global.auth.AuthValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ExperienceReservationService experienceReservationService;
    private final RoomReservationService roomReservationService;
    private final UserService userService;
    private final SellerService sellerService;

    private final ReservationValidator reservationValidator;
    private final AuthValidator authValidator;

    private final SubReservationServiceRegistry subReservationServiceRegistry;





    @Override
    public Reservation findById(Long reservationId) {

        String type = reservationRepository.findReservationTypeById(reservationId);

        return subReservationServiceRegistry.getServiceByType(type).findById(reservationId);
    }


    @Override
    public Reservation create(Member user, CreateReservationDto<?,?> createDto) {

        return createInternal(user, createDto);
    }

    //여기에서 런타입 유형을 기반으로 제네릭 SubReservation,Entity 추론
    //여기 과정 다시 보고 이해하기
    private <SubReservation extends Reservation, Entity>
    Reservation createInternal(Member user, CreateReservationDto<SubReservation, Entity> createDto) {

        SubReservationService<SubReservation, Entity, CreateReservationDto<SubReservation, Entity>> service =
                subReservationServiceRegistry.getServiceByDto(createDto);
        return service.create(user, createDto);
    }



    //예약조회 서비스
    @Override
    public ReservationDetailDto getReservationDetailDto(Member member, Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findById(reservationId);

        if (member instanceof Seller) {

            reservationValidator.ownerSellerValidate(member, reservationId);
            return reservation.toDetailDtoForSeller(countRemainOfParticipant(reservation));
        }

        reservationValidator.ownerUserValidate(member, reservationId);
        return reservation.toDetailDto();
    }

    private int countRemainOfParticipant(Reservation reservation) {

        if (reservation instanceof ExperienceReservation expReservation) {

            return experienceReservationService.countRemain(expReservation.getExperienceRound(),
                    expReservation.getReservationDate());
        }
        return 0;
    }


    @Override
    public Page<? extends ReservationSummaryDto> getReservationSummaryDtoPage(Member member, Pageable pageable,String type) {

        SubReservationService<?,?,?> service = subReservationServiceRegistry.getServiceByType(type);

        if (service == null) {
            return getReservationSummaryDtoPageAll(member, pageable);
        }

        return service.getReservationSummaryDtoPage(member, pageable);

    }

    private Page<? extends ReservationSummaryDto> getReservationSummaryDtoPageAll(Member member, Pageable pageable) {

        if (member instanceof Seller) {
            Page<Reservation> reservationPage = reservationRepository.findReservationPageBySeller(authValidator.sellerValidate(member), pageable)
                    .map(reservation -> findById(reservation.getId()));
            if (!reservationPage.hasContent()) {
                return Page.empty();
            }
            return reservationPage.map(Reservation::toSummaryDtoForSeller);
        }

        Page<Reservation> reservationPage = reservationRepository.findReservationPageByUser(authValidator.userValidate(member), pageable)
                .map(reservation -> findById(reservation.getId()));
        if (!reservationPage.hasContent()) {
            return Page.empty();
        }

        return reservationPage.map(Reservation::toSummaryDto);

    }


    @Override
    public ReservationPersonInfo getReservationPersonInfo(Member user) {
        return ReservationPersonInfo.toDto(user);
    }




    //취소,승인 서비스
    @Override
    public Long approveReservation(Long reservationId, boolean notApproveFlag) {

        Reservation reservation = findById(reservationId);

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


    @Override
    public ReservationCancelResponseDto cancelReservation(Member user, Long reservationId) {

        Reservation reservation = findById(reservationId);

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

        long diffHour = countDiffHourDate(startAt);

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
    @Override
    public void autoChangeCompleteReservation() {

        reservationRepository.findAllConfirmReservation()
                .ifPresent(
                        reservations -> reservations.forEach(this::autoCompleteReservation)
                );
    }

    private void autoCompleteReservation(Reservation reservation) {

        if (checkPastReservation(reservation)) {

            reservation.changeCompleteReservation();
            sellerService.payBackPoint(reservation.getSeller(), reservation.getPrice(),null);
            reservationRepository.save(reservation);
        }
    }

    @Override
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

        return reservationDate.isAfter(LocalDate.now());
    }

    @Override
    public String findTypeById(Long reservationId) {
        return reservationRepository.findReservationTypeById(reservationId);
    }

}
