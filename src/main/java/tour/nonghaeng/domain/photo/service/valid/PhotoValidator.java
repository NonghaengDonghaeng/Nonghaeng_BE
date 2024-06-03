package tour.nonghaeng.domain.photo.service.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.presentation.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.data.repo.PhotoRepository;

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
