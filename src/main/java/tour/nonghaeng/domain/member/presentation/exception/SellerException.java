package tour.nonghaeng.domain.member.presentation.exception;

import tour.nonghaeng.domain.member.presentation.exception.error.SellerErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class SellerException extends NongHaengException {

    public static final SellerException EXCEPTION = new SellerException();

    public SellerException() {
        super(SellerErrorCode.DEFAULT_SELLER_ERROR);
    }

    public SellerException(SellerErrorCode errorCode) {
        super(errorCode);
    }
}
