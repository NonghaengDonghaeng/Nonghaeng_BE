package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode{
    DEFAULT_USER_ERROR(HttpStatus.BAD_GATEWAY,"USER_400_0","user 기본 오류"),
    NO_EXIST_USER_BY_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "USER_400_1", "해당 전화번호의 유저가 존재하지 않습니다."),
    PASSWORD_MISMATCH_ERROR(HttpStatus.BAD_GATEWAY, "USER_100_1", "비밀번호가 불일치 합니다."),
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
