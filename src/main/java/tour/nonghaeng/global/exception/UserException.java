package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.UserErrorCode;

public class UserException extends NongHaengException{

    public static final UserException EXCEPTION = new UserException();

    public UserException() {
        super(UserErrorCode.DEFAULT_USER_ERROR);
    }

    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
