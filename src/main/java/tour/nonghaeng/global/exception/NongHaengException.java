package tour.nonghaeng.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public class NongHaengException extends RuntimeException{

    private BaseErrorCode baseErrorCode;

    public ErrorReason getErrorReason() {
        return this.baseErrorCode.getErrorReason();
    }
}
