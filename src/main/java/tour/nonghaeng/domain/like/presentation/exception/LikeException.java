package tour.nonghaeng.domain.like.presentation.exception;

import tour.nonghaeng.domain.like.presentation.exception.error.LikeErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class LikeException extends NongHaengException {

    public static final LikeException EXCEPTION = new LikeException();
    public LikeException() {
        super(LikeErrorCode.DEFAULT_LIKE_ERROR);
    }

    public LikeException(LikeErrorCode errorCode) {
        super(errorCode);
    }
}
