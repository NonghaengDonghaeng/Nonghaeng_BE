package tour.nonghaeng.domain.photo.service.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.photo.data.ReviewPhoto;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.presentation.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.data.repo.ReviewPhotoRepository;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.global.auth.AuthValidator;

@Component
@RequiredArgsConstructor
public class ReviewPhotoValidator {

    private final ReviewPhotoRepository reviewPhotoRepository;

    private final AuthValidator authValidator;

    public void ownerValidate(Member user, Long reviewPhotoId) {

        reviewPhotoIdValidate(reviewPhotoId);

        ReviewPhoto reviewPhoto = reviewPhotoRepository.findById(reviewPhotoId).get();

        if(!(authValidator.userValidate(user)).equals(reviewPhoto.getUser())) {
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
