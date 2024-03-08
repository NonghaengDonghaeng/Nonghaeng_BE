package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.repo.ExperienceRoundRepository;

@Component
@RequiredArgsConstructor
public class ExperienceRoundValidator {

    private final ExperienceRoundRepository experienceRoundRepository;

    public void createExpRoundDtoValidate(AddExpRoundDto addExpRoundDto) {

    }
}
