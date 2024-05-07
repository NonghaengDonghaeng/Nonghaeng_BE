package tour.nonghaeng.domain.tour.exception;

import tour.nonghaeng.domain.tour.exception.error.TourErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class TourException extends NongHaengException {

    public static final TourException EXCEPTION = new TourException();

    public TourException() {
        super(TourErrorCode.DEFAULT_TOUR_ERROR);
    }

    public TourException(TourErrorCode errorCode) {
        super(errorCode);
    }
}
