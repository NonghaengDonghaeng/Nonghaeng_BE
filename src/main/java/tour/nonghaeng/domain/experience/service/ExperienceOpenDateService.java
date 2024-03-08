package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.AddExpOpenDateDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceOpenDate;
import tour.nonghaeng.domain.experience.repo.ExperienceOpenDateRepository;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.global.validation.ExperienceOpenDateValidator;
import tour.nonghaeng.global.validation.ExperienceValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceOpenDateService {

    private final ExperienceOpenDateRepository experienceOpenDateRepository;
    private final ExperienceOpenDateValidator experienceOpenDateValidator;

    public void addOpenDates(Experience experience, List<AddExpOpenDateDto> openDateDtoList) {
        for (AddExpOpenDateDto openDateDto : openDateDtoList) {
            experience.addOpenDate(createAndSave(experience,openDateDto));
        }
    }

    private ExperienceOpenDate createAndSave(Experience experience, AddExpOpenDateDto openDateDto) {

        experienceOpenDateValidator.openDateDtoValidate(experience,openDateDto);

        ExperienceOpenDate openDate = openDateDto.toEntity(experience);
        return experienceOpenDateRepository.save(openDate);
    }
}
