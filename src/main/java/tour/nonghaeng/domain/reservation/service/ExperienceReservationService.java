package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.service.ExperienceRoundService;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.ExpReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.reservation.ExperienceReservationValidator;

import java.time.LocalDate;
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
        }
        experienceReservation.approveReservation();

        return experienceReservationRepository.save(experienceReservation).getId();

    }

    private ExperienceReservation findById(Long experienceReservationId) {

        return experienceReservationRepository.findById(experienceReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID));
    }
}
