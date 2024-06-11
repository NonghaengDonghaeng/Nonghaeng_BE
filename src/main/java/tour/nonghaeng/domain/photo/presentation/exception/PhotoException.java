package tour.nonghaeng.domain.photo.presentation.exception;

import tour.nonghaeng.domain.photo.presentation.exception.error.PhotoErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class PhotoException extends NongHaengException {

    public static final PhotoException EXCEPTION = new PhotoException();

    public PhotoException() {
        super(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
    }

    public PhotoException(PhotoErrorCode errorCode) {
        super(errorCode);
    }
}
