package tour.nonghaeng.domain.reservation.presentation.exception;

import tour.nonghaeng.domain.reservation.presentation.exception.error.PaymentErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class PaymentException extends NongHaengException {

    public static final PaymentException PAYMENT_EXCEPTION = new PaymentException();

    public PaymentException() {
        super(PaymentErrorCode.DEFAULT_PAYMENT_ERROR);
    }

    public PaymentException(PaymentErrorCode errorCode) {
        super(errorCode);
    }
}
