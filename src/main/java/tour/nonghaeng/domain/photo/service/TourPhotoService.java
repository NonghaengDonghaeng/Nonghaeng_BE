package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.enums.photo.PhotoType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.photo.data.TourPhoto;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.imageServer.service.ImageService;
import tour.nonghaeng.domain.photo.data.repo.TourPhotoRepository;
import tour.nonghaeng.domain.photo.service.valid.PhotoValidator;
import tour.nonghaeng.domain.photo.service.valid.TourPhotoValidator;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.domain.tour.service.valid.TourValidator;
import tour.nonghaeng.global.auth.AuthValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourPhotoService implements PhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.TOUR;

    private final TourPhotoRepository tourPhotoRepository;

    private final TourService tourService;
    private final ImageService imageService;

    private final TourPhotoValidator tourPhotoValidator;
    private final TourValidator tourValidator;
    private final PhotoValidator photoValidator;
    private final AuthValidator authValidator;


    @Override
    public PhotoType getType() {
        return PHOTO_TYPE;
    }

    @Override
    public void uploads(Member seller, Long id, List<MultipartFile> imageFiles) {

        tourValidator.ownerValidate(seller, id);

        Tour tour = tourService.findBySeller(seller);

        for(MultipartFile imageFile : imageFiles) {

            String imgUrl = imageService.uploadImage(PHOTO_TYPE, imageFile);

            createTourPhoto(seller, tour, imgUrl);
        }
    }

    private void createTourPhoto(Member seller, Tour tour, String imgUrl) {

        TourPhoto cretedTourPhoto = TourPhoto.builder()
                .tour(tour)
                .seller(authValidator.sellerValidate(seller))
                .imgUrl(imgUrl)
                .build();

        if (!tourPhotoRepository.hasExactlyOneRepresentativePhoto(tour)) {
            cretedTourPhoto.onRepresentative();
        }

        tourPhotoRepository.save(cretedTourPhoto);
    }


    @Override
    public List<PhotoInfoDto> getPhotoInfoListDto(Long tourId) {

        List<Photo> photoList = tourPhotoRepository.findAllByTour(tourService.findById(tourId));

        photoValidator.emptyPhotoListValidate(photoList);

        return PhotoInfoDto.toDtoList(photoList);
    }



    @Override
    public void changeRepresentativePhoto(Member seller,Long tourPhotoId) {

        tourPhotoValidator.ownerValidate(seller,tourPhotoId);

        TourPhoto tourPhoto = findById(tourPhotoId);
        Tour tour = tourPhoto.getTour();

        tourPhotoValidator.numOfRepresentPhotoValidate(tour);

        tourPhotoRepository.findRepresentativePhotoId(tour)
                .ifPresent(id->{
                    TourPhoto beforeRepresentativePhoto = findById(id);
                    beforeRepresentativePhoto.offRepresentative();
                    tourPhotoRepository.save(beforeRepresentativePhoto);
                });

        tourPhoto.onRepresentative();

        tourPhotoRepository.save(tourPhoto);
    }

    @Override
    public void delete(Member seller, Long photoId) {

        photoValidator.deletePhotoValidate(photoId);
        tourPhotoValidator.ownerValidate(seller,photoId);

        TourPhoto tourPhoto = findById(photoId);

        imageService.deleteImage(PHOTO_TYPE, tourPhoto.getImgUrl());

        tourPhotoRepository.delete(tourPhoto);
    }

    private TourPhoto findById(Long tourPhotoId) {

        return tourPhotoRepository.findById(tourPhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }

    private Photo findPhotoById(Long tourPhotoId) {
        return tourPhotoRepository.findPhotoById(tourPhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }
}
