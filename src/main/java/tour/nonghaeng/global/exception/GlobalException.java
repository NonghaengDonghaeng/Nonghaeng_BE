package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.GlobalErrorCode;

public class GlobalException extends NongHaengException{

    public static final GlobalException EXCEPTION = new GlobalException();

    public GlobalException() {
        super(GlobalErrorCode.ALL_NOT_FOUND);
    }

    public GlobalException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
