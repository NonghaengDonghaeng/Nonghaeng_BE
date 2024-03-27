package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.TourPhoto;
import tour.nonghaeng.domain.photo.repo.TourPhotoRepository;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.validation.photo.PhotoValidator;
import tour.nonghaeng.global.validation.photo.TourPhotoValidator;

import java.util.List;

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
    private final PhotoValidator photoValidator;

    public Long upload(Seller seller, MultipartFile imageFile) {

        Tour tour = tourService.findBySeller(seller);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createTourPhoto(tour, imgUrl).getId();
    }

    public void delete(Long tourPhotoId) {

        amazonS3Service.deleteImage(PHOTO_TYPE,getUrlById(tourPhotoId));

        deleteTourPhoto(tourPhotoId);
    }

    //TODO: 검증로직 작성
    public List<PhotoInfoDto> getTourPhotoInfoListDto(Long tourId) {

        List<Photo> photoList = tourPhotoRepository.findAllByTour(tourService.findById(tourId));

        photoValidator.emptyPhotoListValidate(photoList);

        List<PhotoInfoDto> dto = PhotoInfoDto.toDto(photoList);

        return dto;
    }

    //대표사진이 없으면 대표사진 설정, 대표사진이 있으면 변경
    public void changeRepresentativePhoto(Long tourPhotoId) {

        TourPhoto tourPhoto = findById(tourPhotoId);
        Tour tour = tourPhoto.getTour();

        tourPhotoValidator.changeRepresentativeValidate(tour);

        tourPhotoRepository.findRepresentativePhotoId(tour)
                .ifPresent(id->{
                    TourPhoto beforeRepresentativePhoto = findById(id);
                    beforeRepresentativePhoto.offRepresentative();
                    tourPhotoRepository.save(beforeRepresentativePhoto);
                });

        tourPhoto.onRepresentative();

        tourPhotoRepository.save(tourPhoto);
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
