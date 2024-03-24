package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.S3ErrorCode;

public class S3Exception extends NongHaengException{

    public static final S3Exception EXCEPTION = new S3Exception();

    public S3Exception() {
        super(S3ErrorCode.DEFAULT_S3_ERROR);
    }

    public S3Exception(S3ErrorCode errorCode) {
        super(errorCode);
    }
}
