package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.SellerErrorCode;

public class SellerException extends NongHaengException{

    private static final SellerException EXCEPTION = new SellerException();

    public SellerException() {
        super(SellerErrorCode.DEFAULT_SELLER_ERROR);
    }

    public SellerException(SellerErrorCode errorCode) {
        super(errorCode);
    }
}
