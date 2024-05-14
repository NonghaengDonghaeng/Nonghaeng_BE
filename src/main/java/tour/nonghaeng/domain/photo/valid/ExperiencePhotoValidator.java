package tour.nonghaeng.domain.photo.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;

@Component
@RequiredArgsConstructor
public class ExperiencePhotoValidator {

    private final ExperiencePhotoRepository experiencePhotoRepository;

    public void ownerValidate(Member seller, Long photoId) {

        experiencePhotoIdValidate(photoId);

        Photo photo = experiencePhotoRepository.findPhotoById(photoId).get();

        if (!((Seller) seller).equals(photo.getSeller())) {
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
