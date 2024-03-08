package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.dto.AddExpOpenDateDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceOpenDate;
import tour.nonghaeng.domain.experience.repo.ExperienceOpenDateRepository;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExperienceOpenDateValidator {

    private final ExperienceOpenDateRepository experienceOpenDateRepository;

    public void openDateDtoValidate(Experience experience, AddExpOpenDateDto addExpOpenDateDto) {

        List<ExperienceOpenDate> originOpenDates = experience.getExperienceOpenDates();

        LocalDate shouldValidateDate = addExpOpenDateDto.openDate();
        LocalDate today = LocalDate.now();

        //검증1: 이미 등록된 날짜인지 확인하기
        if (experienceOpenDateRepository.existsByExperienceAndOpenDate(experience, shouldValidateDate)) {
            throw new ExperienceException(ExperienceErrorCode.DUPLICATE_EXPERIENCE_OPEN_DATE_ADD_ERROR);
        }

        //검증2: 과거날짜인지 확인하기
        if (shouldValidateDate.isBefore(today)) {
            throw new ExperienceException(ExperienceErrorCode.PAST_EXPERIENCE_OPEN_DATE_ADD_ERROR);
        }
    }
}
