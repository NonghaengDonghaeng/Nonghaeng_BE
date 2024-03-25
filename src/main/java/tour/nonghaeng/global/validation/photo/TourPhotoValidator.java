package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.TourPhoto;
import tour.nonghaeng.domain.photo.repo.TourPhotoRepository;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.exception.code.PhotoErrorCode;

@Component
@RequiredArgsConstructor
public class TourPhotoValidator {

    private final TourPhotoRepository tourPhotoRepository;

    public void ownerValidate(Seller seller, Long tourPhotoId) {

        tourPhotoIdValidate(tourPhotoId);

        TourPhoto tourPhoto = tourPhotoRepository.findById(tourPhotoId).get();

        if (!seller.equals(tourPhoto.getTour().getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    private void tourPhotoIdValidate(Long tourPhotoId) {
        if (!tourPhotoRepository.existsById(tourPhotoId)) {
            throw new PhotoException(PhotoErrorCode.NO_EXIST_TOUR_PHOTO_BY_ID_ERROR);
        }
    }
}
