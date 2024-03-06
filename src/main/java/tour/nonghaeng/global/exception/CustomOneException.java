package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.GlobalErrorCode;

public class CustomOneException extends NongHaengException{

    public static final CustomOneException EXCEPTION = new CustomOneException();

    public CustomOneException() {
        super(GlobalErrorCode.ALL_NOT_FOUND);
    }
}
