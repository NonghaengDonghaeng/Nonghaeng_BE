package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

public class ExperienceException extends NongHaengException{

    private static final ExperienceException EXCEPTION = new ExperienceException();

    public ExperienceException() {
        super(ExperienceErrorCode.DEFAULT_EXPERIENCE_ERROR);
    }

    public ExperienceException(ExperienceErrorCode errorCode) {
        super(errorCode);
    }
}
