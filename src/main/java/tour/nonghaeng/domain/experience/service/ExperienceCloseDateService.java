package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.AddExpCloseDateDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;
import tour.nonghaeng.domain.experience.repo.ExperienceCloseDateRepository;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;
import tour.nonghaeng.global.validation.ExperienceOpenDateValidator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceCloseDateService {

    private final ExperienceCloseDateRepository experienceCloseDateRepository;
    private final ExperienceOpenDateValidator experienceOpenDateValidator;

    public void addCloseDates(Experience experience, List<AddExpCloseDateDto> closeDateDtoList) {

        experienceOpenDateValidator.addCloseDateDtoValidate(closeDateDtoList);

        for (AddExpCloseDateDto closeDateDto : closeDateDtoList) {
            experience.addCloseDate(createAndSave(experience,closeDateDto));
        }
    }

    public ExperienceCloseDate findByExperienceAndCloseDates(Experience experience, LocalDate closeDate) {

        return experienceCloseDateRepository.findByExperienceAndCloseDate(experience, closeDate).
                orElseThrow(() -> new ExperienceException(ExperienceErrorCode.NOT_EXIST_EXPERIENCE_CLOSE_DATE_ERROR));

    }

    private ExperienceCloseDate createAndSave(Experience experience, AddExpCloseDateDto closeDateDto) {

        experienceOpenDateValidator.createAndSaveValidate(experience,closeDateDto);

        ExperienceCloseDate closeDateEntity = closeDateDto.toEntity(experience);
        return experienceCloseDateRepository.save(closeDateEntity);
    }


}
