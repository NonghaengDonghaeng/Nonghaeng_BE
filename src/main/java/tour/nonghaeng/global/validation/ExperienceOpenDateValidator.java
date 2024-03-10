package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.dto.AddExpOpenDateDto;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceOpenDate;
import tour.nonghaeng.domain.experience.repo.ExperienceOpenDateRepository;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ExperienceOpenDateValidator {

    private final ExperienceOpenDateRepository experienceOpenDateRepository;

    public void addOpenDateDtoValidate(List<AddExpOpenDateDto> addExpOpenDateDtos) {

        LocalDate today = LocalDate.now();
        List<LocalDate> openDateList = addExpOpenDateDtos.stream().map(dto -> dto.openDate()).toList();

        //검증: dto 내 과거날짜가 존재하는지
        if (addExpOpenDateDtos.stream()
                .anyMatch(dto -> dto.openDate().isBefore(today))) {
            throw new ExperienceException(ExperienceErrorCode.PAST_EXPERIENCE_OPEN_DATE_ADD_ERROR);
        }

        //검증: dto 내 중복된 날짜가 존재하는지
        if (addExpOpenDateDtos.size() != addExpOpenDateDtos.stream().distinct().count()) {
            throw new ExperienceException(ExperienceErrorCode.DUPLICATE_EXPERIENCE_OPEN_DATE_ADD_ERROR);
        }
    }

    public void createAndSaveValidate(Experience experience, AddExpOpenDateDto addExpOpenDateDto) {

        LocalDate shouldValidateDate = addExpOpenDateDto.openDate();

        if (isOpenDateRegister(experience, shouldValidateDate)) {
            throw new ExperienceException(ExperienceErrorCode.ALREADY_EXIST_EXPERIENCE_OPEN_DATE_ADD_ERROR);
        }

    }

    private boolean isOpenDateRegister(Experience experience,LocalDate openDate) {
        if (experienceOpenDateRepository.existsByExperienceAndOpenDate(experience, openDate)) {
            return true;
        }
        return false;
    }
}
