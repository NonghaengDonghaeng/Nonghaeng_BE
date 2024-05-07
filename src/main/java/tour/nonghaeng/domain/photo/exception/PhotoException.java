package tour.nonghaeng.domain.photo.exception;

import tour.nonghaeng.domain.photo.exception.error.PhotoErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class PhotoException extends NongHaengException {

    public static final PhotoException EXCEPTION = new PhotoException();

    public PhotoException() {
        super(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
    }

    public PhotoException(PhotoErrorCode errorCode) {
        super(errorCode);
    }
}
