package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;

public class UserException extends NongHaengException{

    public UserException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
