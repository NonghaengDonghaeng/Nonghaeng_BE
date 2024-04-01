package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.service.ExperienceRoundService;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationResponseDto;
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


    public ExpReservationResponseDto createExpReservation(User user, CreateExpReservationDto requestDto) {

        ExperienceRound experienceRound = experienceRoundService.findById(requestDto.getRoundId());

        experienceReservationValidator
                .experienceReservationValidate(
                        experienceRound, user,
                        countRemainOfParticipant(experienceRound, requestDto.getReservationDate()),
                        requestDto);

        userService.payPoint(user, requestDto.getFinalPrice());

        ExperienceReservation experienceReservation = experienceReservationRepository.save(requestDto.toEntity(user, experienceRound));

        return ExpReservationResponseDto.toDto(experienceReservation);
    }

    //해당 날짜, 해당 회차에 잔여인원 구하기
    public int countRemainOfParticipant(ExperienceRound experienceRound, LocalDate localDate) {

        int currentReservationParticipant = 0;

        Optional<Integer> currentNum =
                experienceReservationRepository.countParticipantByExperienceRoundAndReservationDate(experienceRound, localDate);

        if (currentNum.isPresent()){
            currentReservationParticipant = currentNum.get();
        }

        return experienceRound.getMaxParticipant() - currentReservationParticipant;
    }

    private ExperienceReservation findById(Long experienceReservationId) {

        return experienceReservationRepository.findById(experienceReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID));
    }
}
