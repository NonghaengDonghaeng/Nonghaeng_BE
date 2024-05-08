package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.ReviewPhoto;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.repo.ReviewPhotoRepository;
import tour.nonghaeng.domain.photo.s3.AmazonS3Service;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;
import tour.nonghaeng.domain.photo.valid.ReviewPhotoValidator;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.service.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewPhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.REVIEW;

    private final ReviewPhotoRepository reviewPhotoRepository;

    private final ReviewService reviewService;
    private final AmazonS3Service amazonS3Service;

    private final PhotoValidator photoValidator;
    private final ReviewPhotoValidator reviewPhotoValidator;

    public Long upload(User user, Long reviewId, MultipartFile imageFile) {

        Review review = reviewService.findById(reviewId);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createReviewPhoto(user, review, imgUrl).getId();
    }

    private ReviewPhoto createReviewPhoto(User user, Review review, String imgUrl) {

        ReviewPhoto createdReviewPhoto = ReviewPhoto.builder()
                .review(review)
                .user(user)
                .imgUrl(imgUrl)
                .build();

        if (!reviewPhotoRepository.hasExactlyOneRepresentativePhoto(review)) {
            createdReviewPhoto.onRepresentative();
        }

        return reviewPhotoRepository.save(createdReviewPhoto);
    }

    public List<PhotoInfoDto> getReviewPhotoInfoListDto(Long reviewId) {

        List<Photo> photoList = reviewPhotoRepository.findAllByReview(reviewService.findById(reviewId));

        photoValidator.emptyPhotoListValidate(photoList);

        List<PhotoInfoDto> dto = PhotoInfoDto.toDtoList(photoList);

        return dto;
    }

    public void changeRepresentativePhoto(Long reviewId) {

        ReviewPhoto reviewPhoto = findById(reviewId);
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

    public ReviewPhoto findById(Long reviewId) {
        return reviewPhotoRepository.findById(reviewId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }

    public Photo findPhotoById(Long reviewId) {
        return reviewPhotoRepository.findPhotoById(reviewId)
                .orElseThrow(()-> PhotoException.EXCEPTION);
    }


}
