package tour.nonghaeng.domain.member.exception;

import tour.nonghaeng.domain.member.exception.error.SellerErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class SellerException extends NongHaengException {

    public static final SellerException EXCEPTION = new SellerException();

    public SellerException() {
        super(SellerErrorCode.DEFAULT_SELLER_ERROR);
    }

    public SellerException(SellerErrorCode errorCode) {
        super(errorCode);
    }
}
