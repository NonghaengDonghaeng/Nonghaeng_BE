package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.repo.PhotoRepository;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.exception.code.PhotoErrorCode;

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

        PhotoType photoType = PhotoType.ofDtype(photoRepository.findPhotoType(photoId).get());


        //TODO: 현재 photo 객체에서만 소유자를 확인할 수 없어서 각각 하위객체를 통해 알아본다.
        //if 문이 싫다면 photo 객체에 Seller 연관관계 추가하기
        if (photoType.equals(PhotoType.TOUR)) {
            tourPhotoValidator.ownerValidate(seller,photoId);
        } else if (photoType.equals(PhotoType.ROOM)) {
            roomPhotoValidator.ownerValidate(seller, photoId);
        }else {
            experiencePhotoValidator.ownerValidate(seller,photoId);
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
