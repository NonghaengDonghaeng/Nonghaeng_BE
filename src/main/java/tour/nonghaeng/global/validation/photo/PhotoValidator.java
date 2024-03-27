package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.exception.code.PhotoErrorCode;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PhotoValidator {

    public void emptyPhotoListValidate(List<Photo> photoList) {

        if (photoList.isEmpty()) {
            throw new PhotoException(PhotoErrorCode.EMPTY_PHOTO_LIST_ERROR);
        }
    }

}
