package tour.nonghaeng.domain.photo.s3.exception;

import tour.nonghaeng.domain.photo.s3.exception.error.S3ErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class S3Exception extends NongHaengException {

    public static final S3Exception EXCEPTION = new S3Exception();

    public S3Exception() {
        super(S3ErrorCode.DEFAULT_S3_ERROR);
    }

    public S3Exception(S3ErrorCode errorCode) {
        super(errorCode);
    }
}
