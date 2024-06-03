package tour.nonghaeng.domain.experience.presentation.exception;

import tour.nonghaeng.domain.experience.presentation.exception.error.ExperienceErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class ExperienceException extends NongHaengException {

    public static final ExperienceException EXCEPTION = new ExperienceException();

    public ExperienceException() {
        super(ExperienceErrorCode.DEFAULT_EXPERIENCE_ERROR);
    }

    public ExperienceException(ExperienceErrorCode errorCode) {
        super(errorCode);
    }
}
