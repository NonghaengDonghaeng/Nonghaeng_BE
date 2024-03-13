package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.ReservationErrorCode;

public class ReservationException extends NongHaengException {

    public static final ReservationException EXCEPTION = new ReservationException();

    public ReservationException() {
        super(ReservationErrorCode.DEFAULT_RESERVATION_ERROR);
    }

    public ReservationException(ReservationErrorCode errorCode) {
        super(errorCode);
    }
}
