package tour.nonghaeng.domain.photo.imageServer.exception;

import tour.nonghaeng.global.infra.exception.NongHaengException;

public class ImageServerException extends NongHaengException {

    public static final ImageServerException EXCEPTION = new ImageServerException();

    public ImageServerException() {
        super(ImageServerErrorCode.DEFAULT_S3_ERROR);
    }

    public ImageServerException(ImageServerErrorCode errorCode) {
        super(errorCode);
    }
}
