package tour.nonghaeng.domain.photo.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.repo.PhotoRepository;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.exception.error.PhotoErrorCode;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PhotoValidator {

    private final PhotoRepository photoRepository;

    private final TourPhotoValidator tourPhotoValidator;
    private final RoomPhotoValidator roomPhotoValidator;
    private final ExperiencePhotoValidator experiencePhotoValidator;

    public void ownerValidate(Seller seller, Long photoId) {

        photoIdValidate(photoId);

        Photo photo = photoRepository.findById(photoId).get();

        if (!seller.equals(photo.getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

    }

    public void emptyPhotoListValidate(List<Photo> photoList) {

        if (photoList.isEmpty()) {
            throw new PhotoException(PhotoErrorCode.EMPTY_PHOTO_LIST_ERROR);
        }
    }

    public void deletePhotoValidate(Long photoId) {

        if (photoRepository.isRepresentById(photoId)) {
            throw new PhotoException(PhotoErrorCode.CANT_DELETE_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    private void photoIdValidate(Long photoId) {

        if (!photoRepository.existsById(photoId)) {

            throw new PhotoException(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
        }
    }

}
