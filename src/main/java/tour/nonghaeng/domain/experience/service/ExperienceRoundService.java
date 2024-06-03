package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.experience.data.repo.ExperienceRoundRepository;
import tour.nonghaeng.domain.experience.presentation.exception.ExperienceException;
import tour.nonghaeng.domain.experience.presentation.exception.error.ExperienceErrorCode;
import tour.nonghaeng.domain.experience.service.valid.ExperienceRoundValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceRoundService {

    private final ExperienceRoundRepository experienceRoundRepository;

    private final ExperienceRoundValidator experienceRoundValidator;


    public void addRounds(Experience experience, List<AddExpRoundDto> dtoList) {

        for (AddExpRoundDto dto : dtoList) {
            experience.addExperienceRound(createAndSaveRound(experience,dto));
        }
    }

    private ExperienceRound createAndSaveRound(Experience experience, AddExpRoundDto dto) {

        experienceRoundValidator.createAndSaveValidate(experience,dto);

        ExperienceRound round = dto.toEntity(experience);
        return experienceRoundRepository.save(round);
    }



    public ExperienceRound findById(Long experienceRoundId) {

        return experienceRoundRepository.findById(experienceRoundId)
                .orElseThrow(() -> new ExperienceException(ExperienceErrorCode.NO_EXIST_EXPERIENCE_ROUND_BY_ID));
    }


}
