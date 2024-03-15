package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.service.ExperienceRoundService;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.reservation.ExperienceReservationValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceReservationService {

    private final ExperienceReservationRepository experienceReservationRepository;

    private final ExperienceRoundService experienceRoundService;
    private final UserService userService;

    private final ExperienceReservationValidator experienceReservationValidator;

    public ExpReservationResponseDto reserveExperience(User user, CreateExpReservationDto requestDto) {

        ExperienceRound experienceRound = experienceRoundService.findById(requestDto.getRoundId());

        experienceReservationValidator
                .experienceReservationValidate(
                        experienceRound, user,
                        countRemainOfParticipant(experienceRound, requestDto.getReservationDate()),
                        requestDto);

        //포인트 차감로직(결제)
        userService.payPoint(user, requestDto.getFinalPrice());

        ExperienceReservation experienceReservation = experienceReservationRepository.save(requestDto.toEntity(user, experienceRound));

        return ExpReservationResponseDto.toDto(experienceReservation);

    }


    //해당 날짜, 해당 회차에 잔여인원 구하기
    public int countRemainOfParticipant(ExperienceRound experienceRound, LocalDate localDate) {

        int currentReservationParticipant = 0;
        Optional<Integer> currentNum = experienceReservationRepository.countParticipantByExperienceRoundAndReservationDate(experienceRound, localDate);
        if (currentNum.isPresent()){
            currentReservationParticipant = currentNum.get();
        }

        return experienceRound.getMaxParticipant() - currentReservationParticipant;
    }

    public Page<ExpReservationSellerSummaryDto> showExpReservationSummaryList(Seller seller, Pageable pageable) {

        Page<ExperienceReservation> page = experienceReservationRepository.findAllBySeller(seller, pageable);

        experienceReservationValidator.pageValidate(page);

        return ExpReservationSellerSummaryDto.toPageDto(page);
    }

    public Long approveExpReservation(Long expReservationId, boolean notApproveFlag) {
        ExperienceReservation experienceReservation = findById(expReservationId);

        //검증: 예약 대기 상태인지 확인
        experienceReservationValidator.checkWaitingState(experienceReservation);

        if (notApproveFlag) {
            experienceReservation.notApproveReservation();
            userService.payBackPoint(experienceReservation.getUser(), experienceReservation.getPrice(), CancelPolicy.NOT_CONFIRM_CANCEL_POLICY);
        }
        experienceReservation.approveReservation();

        return experienceReservationRepository.save(experienceReservation).getId();

    }

    private ExperienceReservation findById(Long experienceReservationId) {

        return experienceReservationRepository.findById(experienceReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID));
    }

    public ExpReservationSellerDetailDto getExpReservationSellerDetailDto(Long experienceReservationId) {

        ExperienceReservation experienceReservation = findById(experienceReservationId);

        int remainOfParticipant = countRemainOfParticipant(experienceReservation.getExperienceRound(), experienceReservation.getReservationDate());

        return ExpReservationSellerDetailDto.toDto(experienceReservation,remainOfParticipant);
    }

    public ExpReservationCancelResponseDto cancelExpReservation(User user, Long experienceReservationId) {

        ExperienceReservation experienceReservation = findById(experienceReservationId);

        experienceReservationValidator.checkCancelState(experienceReservation);

        CancelPolicy cancelPolicy = decideCancelPolicy(experienceReservation);

        userService.payBackPoint(user, experienceReservation.getPrice(), cancelPolicy);

        experienceReservation.cancelReservation();

        experienceReservationRepository.save(experienceReservation);

        return ExpReservationCancelResponseDto.toDto(experienceReservation, cancelPolicy);

    }

    private Long countDiffHourDate(LocalDateTime startAt) {

        Duration duration = Duration.between(LocalDateTime.now(), startAt);

        return duration.getSeconds() / 3600;
    }

    private CancelPolicy decideCancelPolicy(ExperienceReservation experienceReservation) {

        LocalDate reservationAt = experienceReservation.getCreatedAt().toLocalDate();
        LocalDateTime experienceStartAt = LocalDateTime
                .of(experienceReservation.getReservationDate(), experienceReservation.getExperienceRound().getStartTime());

        Long diffHour = countDiffHourDate(experienceStartAt);

        //예약 대기중일 경우 수수료 없음
        if (experienceReservation.getStateType().equals(ReservationStateType.WAITING_RESERVATION)) {
            return CancelPolicy.NOT_CONFIRM_CANCEL_POLICY;
        }
        //당일 취소인 경우 수수료 없음
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
}
