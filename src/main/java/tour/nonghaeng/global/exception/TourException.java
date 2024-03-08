package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.TourErrorCode;

public class TourException extends NongHaengException{

    public static final TourException EXCEPTION = new TourException();

    public TourException() {
        super(TourErrorCode.DEFAULT_TOUR_ERROR);
    }

    public TourException(TourErrorCode errorCode) {
        super(errorCode);
    }
}
