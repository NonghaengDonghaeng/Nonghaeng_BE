package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.TourPhoto;
import tour.nonghaeng.domain.photo.repo.TourPhotoRepository;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.validation.photo.TourPhotoValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourPhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.TOUR;

    private final TourPhotoRepository tourPhotoRepository;

    private final TourService tourService;
    private final AmazonS3Service amazonS3Service;

    private final TourPhotoValidator tourPhotoValidator;

    public Long upload(Seller seller, MultipartFile imageFile) {

        Tour tour = tourService.findBySeller(seller);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createTourPhoto(tour, imgUrl).getId();
    }

    public void delete(Seller seller, Long tourPhotoId) {

        tourPhotoValidator.ownerValidate(seller,tourPhotoId);

        amazonS3Service.deleteImage(PHOTO_TYPE,getUrlById(tourPhotoId));

        deleteTourPhoto(tourPhotoId);
    }

    private TourPhoto createTourPhoto(Tour tour, String imgUrl) {

        return tourPhotoRepository.save(TourPhoto.builder()
                .tour(tour)
                .imgUrl(imgUrl)
                .build());
    }

    private void deleteTourPhoto(Long tourPhotoId) {

        tourPhotoRepository.delete(findById(tourPhotoId));
    }

    private String getUrlById(Long tourPhotoId) {

        return findById(tourPhotoId).getImgUrl();
    }

    public TourPhoto findById(Long tourPhotoId) {

        return tourPhotoRepository.findById(tourPhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }

}
