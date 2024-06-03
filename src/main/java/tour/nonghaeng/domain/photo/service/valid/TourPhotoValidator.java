package tour.nonghaeng.domain.photo.service.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.presentation.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.data.repo.TourPhotoRepository;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.auth.valid.AuthValidator;

@Component
@RequiredArgsConstructor
public class TourPhotoValidator {

    private final TourPhotoRepository tourPhotoRepository;

    private final AuthValidator authValidator;


    public void ownerValidate(Member seller, Long photoId) {

        tourPhotoIdValidate(photoId);

        Photo photo = tourPhotoRepository.findPhotoById(photoId).get();

        if (!(authValidator.sellerValidate(seller)).equals(photo.getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void numOfRepresentPhotoValidate(Tour tour) {

        if (!tourPhotoRepository.hasExactlyOneRepresentativePhoto(tour)) {
            throw new PhotoException(PhotoErrorCode.WRONG_NUM_OF_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    public void deleteValidate(Tour tour,Long tourPhotoId) {

        //대표사진은 삭제할 수 없음
        if (tourPhotoRepository.findRepresentativePhotoId(tour).get()
                .equals(tourPhotoId)) {

            throw new PhotoException(PhotoErrorCode.CANT_DELETE_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    private void tourPhotoIdValidate(Long tourPhotoId) {
        if (!tourPhotoRepository.existsById(tourPhotoId)) {
            throw new PhotoException(PhotoErrorCode.NO_EXIST_TOUR_PHOTO_BY_ID_ERROR);
        }
    }
}
