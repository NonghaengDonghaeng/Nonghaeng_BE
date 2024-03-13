package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.service.ExperienceRoundService;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
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

    private final ExperienceReservationValidator experienceReservationValidator;

    public ExpReservationResponseDto reserveExperience(User user, CreateExpReservationDto requestDto) {

        ExperienceRound experienceRound = experienceRoundService.findById(requestDto.getRoundId());

        experienceReservationValidator
                .createExpReservationDtoValidate(
                        experienceRound,
                        countRemainOfParticipant(experienceRound, requestDto.getReservationDate()),
                        requestDto);

        //포인트 차감로직(결제) - 이 안에 결제관련 검증 예외 필요

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
}
