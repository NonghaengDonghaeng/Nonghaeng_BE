package tour.nonghaeng.domain.review.exception;

import tour.nonghaeng.domain.review.exception.error.ReviewErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class ReviewException extends NongHaengException {

    public static final ReviewException EXCEPTION = new ReviewException();

    public ReviewException() {
        super(ReviewErrorCode.DEFAULT_REVIEW_ERROR);
    }

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}
