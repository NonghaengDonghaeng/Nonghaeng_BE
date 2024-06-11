package tour.nonghaeng.global.infra.exception.error;

import tour.nonghaeng.global.infra.exception.dto.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getErrorReason();
}
