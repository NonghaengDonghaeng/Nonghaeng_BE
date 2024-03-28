package tour.nonghaeng.global.validation.experience;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.repo.ExperienceRoundRepository;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExperienceRoundValidator {

    private final ExperienceRoundRepository experienceRoundRepository;


    public void createAndSaveValidate(Experience experience, AddExpRoundDto addExpRoundDto) {

        if (!experienceRoundRepository.existsByExperience(experience)) {
            return;
        }

        List<ExperienceRound> originExperienceRounds = experienceRoundRepository.findAllByExperienceOrderByStartTime(experience);

        LocalTime startPoint = addExpRoundDto.startTime();
        LocalTime endPoint = addExpRoundDto.endTime() == null ? addExpRoundDto.startTime().plusHours(2) : addExpRoundDto.endTime();

        //검증1: 이미 등록된 회차에 안 겹치는지 확인
        for (ExperienceRound round : originExperienceRounds) {
            log.info(round.getStartTime().toString());

            if(startPoint.isBefore(round.getStartTime())){
                if(!endPoint.isBefore(round.getStartTime())){
                    throw new ExperienceException(ExperienceErrorCode.OVERLAPS_ROUND_TIME_ADD_ERROR);
                }
                break;
            }
            if (startPoint.isBefore(round.getEndTime())) {
                throw new ExperienceException(ExperienceErrorCode.OVERLAPS_ROUND_TIME_ADD_ERROR);
            }
        }
    }
}
