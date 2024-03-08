package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;

@RequiredArgsConstructor
@Component
public class ExperienceValidator {

    private final ExperienceRepository experienceRepository;
}
