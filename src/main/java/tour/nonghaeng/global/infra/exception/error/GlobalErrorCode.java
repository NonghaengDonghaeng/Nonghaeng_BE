package tour.nonghaeng.global.infra.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode implements BaseErrorCode{

    ALL_NOT_FOUND(HttpStatus.NOT_FOUND,"GLOBAL_404_1","전체 오류"),

    MEMBER_DOWN_CASTING_ERROR(HttpStatus.BAD_GATEWAY,"GLOBAL_400_1","멤버 다운캐스팅 오류"),

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
