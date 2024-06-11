package tour.nonghaeng.global.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public class NongHaengException extends RuntimeException{

    private BaseErrorCode baseErrorCode;

    public ErrorReason getErrorReason() {
        return this.baseErrorCode.getErrorReason();
    }
}
