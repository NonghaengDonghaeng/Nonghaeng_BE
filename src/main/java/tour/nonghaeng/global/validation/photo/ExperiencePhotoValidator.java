package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;

@Component
@RequiredArgsConstructor
public class ExperiencePhotoValidator {

    private final ExperiencePhotoRepository experiencePhotoRepository;


}
