package tour.nonghaeng.domain.photo.service.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.member.data.Admin;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.presentation.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.data.repo.ExperiencePhotoRepository;
import tour.nonghaeng.global.auth.AuthValidator;

@Component
@RequiredArgsConstructor
public class ExperiencePhotoValidator {

    private final ExperiencePhotoRepository experiencePhotoRepository;

    private final AuthValidator authValidator;

    public void ownerValidate(Member seller, Long photoId) {

        experiencePhotoIdValidate(photoId);

        Photo photo = experiencePhotoRepository.findPhotoById(photoId).get();

        if(seller instanceof Admin){
            return;
        }

        if (!(authValidator.sellerValidate(seller)).equals(photo.getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void numOfRepresentPhotoValidate(Experience experience) {

        if (!experiencePhotoRepository.hasExactlyOneRepresentativePhoto(experience)) {
            throw new PhotoException(PhotoErrorCode.WRONG_NUM_OF_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    public void deleteValidate(Experience experience, Long experiencePhotoId) {

        if (experiencePhotoRepository.findRepresentativePhotoId(experience).get()
                .equals(experiencePhotoId)) {
            throw new PhotoException(PhotoErrorCode.CANT_DELETE_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    private void experiencePhotoIdValidate(Long experiencePhotoId) {
        if (!experiencePhotoRepository.existsById(experiencePhotoId)) {
            throw new PhotoException(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
        }
    }
}
