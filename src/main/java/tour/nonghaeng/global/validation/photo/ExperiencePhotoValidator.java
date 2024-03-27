package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.ExperiencePhoto;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.exception.code.PhotoErrorCode;

@Component
@RequiredArgsConstructor
public class ExperiencePhotoValidator {

    private final ExperiencePhotoRepository experiencePhotoRepository;

    public void ownerValidate(Seller seller, Long experiencePhotoId) {

        experiencePhotoIdValidate(experiencePhotoId);

        ExperiencePhoto experiencePhoto = experiencePhotoRepository.findById(experiencePhotoId).get();

        if (!seller.equals(experiencePhoto.getExperience().getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void changeRepresentativeValidate(Experience experience) {

        if (!experiencePhotoRepository.hasExactlyOneRepresentativePhoto(experience)) {
            throw new PhotoException(PhotoErrorCode.WRONG_NUM_OF_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    private void experiencePhotoIdValidate(Long experiencePhotoId) {
        if (!experiencePhotoRepository.existsById(experiencePhotoId)) {
            throw new PhotoException(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
        }
    }
}
