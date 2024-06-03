package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.AddExpCloseDateDto;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;
import tour.nonghaeng.domain.experience.data.repo.ExperienceCloseDateRepository;
import tour.nonghaeng.domain.experience.presentation.exception.ExperienceException;
import tour.nonghaeng.domain.experience.presentation.exception.error.ExperienceErrorCode;
import tour.nonghaeng.domain.experience.service.valid.ExperienceCloseDateValidator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceCloseDateService {

    private final ExperienceCloseDateRepository experienceCloseDateRepository;

    private final ExperienceCloseDateValidator experienceCloseDateValidator;




    public void addCloseDates(Experience experience, List<AddExpCloseDateDto> closeDateDtoList) {

        experienceCloseDateValidator.defaultCloseDateDtoValidate(closeDateDtoList);

        for (AddExpCloseDateDto closeDateDto : closeDateDtoList) {
            experience.addCloseDate(createAndSave(experience,closeDateDto));
        }
    }

    private ExperienceCloseDate createAndSave(Experience experience, AddExpCloseDateDto closeDateDto) {

        experienceCloseDateValidator.createAndSaveValidate(experience,closeDateDto);

        ExperienceCloseDate closeDateEntity = closeDateDto.toEntity(experience);

        return experienceCloseDateRepository.save(closeDateEntity);
    }



    public ExperienceCloseDate findByExperienceAndCloseDate(Experience experience, LocalDate closeDate) {

        return experienceCloseDateRepository.findByExperienceAndCloseDate(experience, closeDate).
                orElseThrow(() -> new ExperienceException(ExperienceErrorCode.NOT_EXIST_EXPERIENCE_CLOSE_DATE_ERROR));
    }


}
