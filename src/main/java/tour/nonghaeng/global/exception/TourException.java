package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.TourErrorCode;

public class TourException extends NongHaengException{

    public TourException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
