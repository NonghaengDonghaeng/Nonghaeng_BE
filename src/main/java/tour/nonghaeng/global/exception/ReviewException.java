package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.ReviewErrorCode;

public class ReviewException extends NongHaengException {

    public static final ReviewException EXCEPTION = new ReviewException();

    public ReviewException() {
        super(ReviewErrorCode.DEFAULT_REVIEW_ERROR);
    }

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}
