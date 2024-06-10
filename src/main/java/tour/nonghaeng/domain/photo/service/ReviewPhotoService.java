package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.enums.photo.PhotoType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.photo.data.ReviewPhoto;
import tour.nonghaeng.domain.photo.data.repo.ReviewPhotoRepository;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.imageServer.service.ImageService;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.service.valid.PhotoValidator;
import tour.nonghaeng.domain.photo.service.valid.ReviewPhotoValidator;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.service.ReviewService;
import tour.nonghaeng.domain.review.service.valid.ReviewValidator;
import tour.nonghaeng.global.auth.AuthValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewPhotoService implements PhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.REVIEW;

    private final ReviewPhotoRepository reviewPhotoRepository;

    private final ReviewService reviewService;
    private final ImageService imageService;

    private final PhotoValidator photoValidator;
    private final ReviewPhotoValidator reviewPhotoValidator;
    private final ReviewValidator reviewValidator;
    private final AuthValidator authValidator;


    //TODO: delete api 추가하기

    @Override
    public PhotoType getType() {
        return PHOTO_TYPE;
    }

    @Override
    public void uploads(Member user, Long reviewId, List<MultipartFile> imageFiles) {

        reviewValidator.ownerValidate(user,reviewId);

        Review review = reviewService.findById(reviewId);

        for(MultipartFile imageFile : imageFiles) {

            String imgUrl = imageService.uploadImage(PHOTO_TYPE, imageFile);

            createReviewPhoto(user, review, imgUrl);
        }
    }

    private void createReviewPhoto(Member user, Review review, String imgUrl) {

        ReviewPhoto createdReviewPhoto = ReviewPhoto.builder()
                .review(review)
                .user(authValidator.userValidate(user))
                .imgUrl(imgUrl)
                .build();

        if (!reviewPhotoRepository.hasExactlyOneRepresentativePhoto(review)) {
            createdReviewPhoto.onRepresentative();
        }

        reviewPhotoRepository.save(createdReviewPhoto);
    }


    @Override
    public List<PhotoInfoDto> getPhotoInfoListDto(Long reviewId) {

        List<Photo> photoList = reviewPhotoRepository.findAllByReview(reviewService.findById(reviewId));

        photoValidator.emptyPhotoListValidate(photoList);

        return PhotoInfoDto.toDtoList(photoList);
    }


    @Override
    public void changeRepresentativePhoto(Member user, Long reviewPhotoId) {

        reviewPhotoValidator.ownerValidate(user, reviewPhotoId);

        ReviewPhoto reviewPhoto = findById(reviewPhotoId);
        Review review = reviewPhoto.getReview();


        reviewPhotoValidator.numOfRepresentPhotoValidate(review);

        reviewPhotoRepository.findRepresentativePhotoId(review)
                .ifPresent(id->{
                    ReviewPhoto beforeRepresentativePhoto = findById(id);
                    beforeRepresentativePhoto.offRepresentative();
                    reviewPhotoRepository.save(beforeRepresentativePhoto);
                });

        reviewPhoto.onRepresentative();

        reviewPhotoRepository.save(reviewPhoto);
    }

    @Override
    public void delete(Member user, Long photoId) {

        photoValidator.deletePhotoValidate(photoId);
        reviewPhotoValidator.ownerValidate(user,photoId);

        ReviewPhoto reviewPhoto = findById(photoId);

        imageService.deleteImage(PHOTO_TYPE, reviewPhoto.getImgUrl());

        reviewPhotoRepository.delete(reviewPhoto);
    }

    private ReviewPhoto findById(Long reviewId) {
        return reviewPhotoRepository.findById(reviewId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }

    private Photo findPhotoById(Long reviewId) {
        return reviewPhotoRepository.findPhotoById(reviewId)
                .orElseThrow(()-> PhotoException.EXCEPTION);
    }
}
