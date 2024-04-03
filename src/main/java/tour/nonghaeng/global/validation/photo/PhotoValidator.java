package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

    public void emptyPhotoListValidate(List<Photo> photoList) {

        if (photoList.isEmpty()) {
            throw new PhotoException(PhotoErrorCode.EMPTY_PHOTO_LIST_ERROR);
        }
    }

    public void deletePhotoValidate(Long photoId) {

        if (photoRepository.findRepresentById(photoId)) {
            throw new PhotoException(PhotoErrorCode.CANT_DELETE_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

}
