package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.dto.AddExpCloseDateDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.repo.ExperienceCloseDateRepository;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExperienceOpenDateValidator {

    private final ExperienceCloseDateRepository experienceCloseDateRepository;

    public void addCloseDateDtoValidate(List<AddExpCloseDateDto> addExpCloseDateDtos) {

        LocalDate today = LocalDate.now();
        List<LocalDate> closeDateList = addExpCloseDateDtos.stream().map(dto -> dto.closeDate()).toList();

        //검증: dto 내 과거날짜가 존재하는지
        if (addExpCloseDateDtos.stream()
                .anyMatch(dto -> dto.closeDate().isBefore(today))) {
            throw new ExperienceException(ExperienceErrorCode.PAST_EXPERIENCE_CLOSE_DATE_ADD_ERROR);
        }

        //검증: dto 내 중복된 날짜가 존재하는지
        if (addExpCloseDateDtos.size() != addExpCloseDateDtos.stream().distinct().count()) {
            throw new ExperienceException(ExperienceErrorCode.DUPLICATE_EXPERIENCE_CLOSE_DATE_ADD_ERROR);
        }
    }

    public void createAndSaveValidate(Experience experience, AddExpCloseDateDto addExpCloseDateDto) {

        LocalDate shouldValidateDate = addExpCloseDateDto.closeDate();

        if (isCloseDateRegister(experience, shouldValidateDate)) {
            throw new ExperienceException(ExperienceErrorCode.ALREADY_EXIST_EXPERIENCE_CLOSE_DATE_ADD_ERROR);
        }

        if (shouldValidateDate.isBefore(experience.getStartDate()) || shouldValidateDate.isAfter(experience.getEndDate())) {
            throw new ExperienceException(ExperienceErrorCode.ALREADY_NOT_RUNNING_DATE_ADD_ERROR);
        }

    }

    public void dateParameterValidate(Experience experience, LocalDate dateParam) {

        //오늘 이후인지
        if (dateParam.isBefore(LocalDate.now())) {
            throw new ExperienceException(ExperienceErrorCode.WRONG_DATE_PARAMETER_BY_PAST_DATE);
        }
        //운영기간에 속하는지
        if (dateParam.isBefore(experience.getStartDate()) || dateParam.isAfter(experience.getEndDate())) {
            throw new ExperienceException(ExperienceErrorCode.WRONG_DATE_PARAMETERS_BY_NOT_RUNNING_DATE);
        }
        //운영종료 리스트에 들어가있는지
        if (isCloseDateRegister(experience, dateParam)) {
            throw new ExperienceException(ExperienceErrorCode.WRONG_DATE_PARAMETERS_BY_NOT_RUNNING_DATE);
        }
    }

    private boolean isCloseDateRegister(Experience experience,LocalDate closeDate) {
        if (experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, closeDate)) {
            return true;
        }
        return false;
    }
}
