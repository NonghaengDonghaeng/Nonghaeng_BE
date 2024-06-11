package tour.nonghaeng.domain.notice.presentation.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;

@AllArgsConstructor
@Getter
public enum NoticeErrorCode implements BaseErrorCode {

    DEFAULT_NOTICE_ERROR(HttpStatus.BAD_GATEWAY,"NOTICE_400_0","공지 기본 오류"),
    ;

    private HttpStatus status;
    private String code;
    private String reason;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(status.value())
                .code(this.code)
                .reason(this.reason)
                .build();
    }
}
