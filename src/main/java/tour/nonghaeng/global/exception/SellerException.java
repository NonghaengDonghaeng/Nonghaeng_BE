package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;

public class SellerException extends NongHaengException{

    public SellerException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
