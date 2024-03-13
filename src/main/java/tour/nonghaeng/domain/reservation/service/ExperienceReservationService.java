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

        //requestDto 검증로직
        // 1. 가격이 맞는지 확인
        // 해당날짜가 운영하는지 안하는지 다시 확인
        // 인원이 충분한지

        //포인트 차감로직

        //예약
        ExperienceReservation experienceReservation = requestDto.toEntity(user, experienceRound);

        experienceReservationRepository.save(experienceReservation);

        ExpReservationResponseDto responseDto = ExpReservationResponseDto.toDto(experienceReservationRepository.save(experienceReservation));

        return responseDto;

    }
}
