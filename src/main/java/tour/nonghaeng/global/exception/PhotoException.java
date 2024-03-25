package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.PhotoErrorCode;

public class PhotoException extends NongHaengException{

    public static final PhotoException EXCEPTION = new PhotoException();

    public PhotoException() {
        super(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
    }

    public PhotoException(PhotoErrorCode errorCode) {
        super(errorCode);
    }
}
