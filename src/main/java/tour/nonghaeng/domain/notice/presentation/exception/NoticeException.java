package tour.nonghaeng.domain.notice.presentation.exception;

import tour.nonghaeng.domain.notice.presentation.exception.error.NoticeErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class NoticeException extends NongHaengException {

    public static final NoticeException EXCEPTION = new NoticeException();



    public NoticeException() {
        super(NoticeErrorCode.DEFAULT_NOTICE_ERROR);
    }

    public NoticeException(NoticeErrorCode errorCode) {
        super(errorCode);
    }
}
