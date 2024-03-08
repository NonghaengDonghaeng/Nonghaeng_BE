package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum ExperienceErrorCode implements BaseErrorCode{
    DEFAULT_EXPERIENCE_ERROR(HttpStatus.BAD_GATEWAY,"EXP_400_0","체험 기본 오류"),
    ;

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(this.status.value())
                .code(this.code)
                .reason(this.reason)
                .build();
    }
}
