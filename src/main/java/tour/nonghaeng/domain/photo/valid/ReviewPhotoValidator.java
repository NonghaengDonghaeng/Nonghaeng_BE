package tour.nonghaeng.domain.photo.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.photo.entity.ReviewPhoto;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.repo.ReviewPhotoRepository;
import tour.nonghaeng.domain.review.entity.Review;

@Component
@RequiredArgsConstructor
public class ReviewPhotoValidator {

    private final ReviewPhotoRepository reviewPhotoRepository;

    public void ownerValidate(User user, Long reviewPhotoId) {

        reviewPhotoIdValidate(reviewPhotoId);

        ReviewPhoto reviewPhoto = reviewPhotoRepository.findById(reviewPhotoId).get();

        if(!user.equals(reviewPhoto.getUser())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void numOfRepresentPhotoValidate(Review review) {

        if (!reviewPhotoRepository.hasExactlyOneRepresentativePhoto(review)) {
            throw new PhotoException(PhotoErrorCode.WRONG_NUM_OF_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    private void reviewPhotoIdValidate(Long reviewPhotoId) {
        if(!reviewPhotoRepository.existsById(reviewPhotoId)) {
            throw new PhotoException(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
        }
    }
}
