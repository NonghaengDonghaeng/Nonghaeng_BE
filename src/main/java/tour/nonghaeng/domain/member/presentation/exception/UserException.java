package tour.nonghaeng.domain.member.presentation.exception;

import tour.nonghaeng.domain.member.presentation.exception.error.UserErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class UserException extends NongHaengException {

    public static final UserException EXCEPTION = new UserException();

    public UserException() {
        super(UserErrorCode.DEFAULT_USER_ERROR);
    }

    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
