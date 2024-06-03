package tour.nonghaeng.domain.reservation.presentation.exception;

import tour.nonghaeng.domain.reservation.presentation.exception.error.ReservationErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class ReservationException extends NongHaengException {

    public static final ReservationException EXCEPTION = new ReservationException();

    public ReservationException() {
        super(ReservationErrorCode.DEFAULT_RESERVATION_ERROR);
    }

    public ReservationException(ReservationErrorCode errorCode) {
        super(errorCode);
    }
}
