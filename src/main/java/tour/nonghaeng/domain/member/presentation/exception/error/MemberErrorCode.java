package tour.nonghaeng.domain.member.presentation.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements BaseErrorCode {
    DEFAULT_MEMBER_ERROR(HttpStatus.BAD_GATEWAY,"MEMBER_400_1","member 기본 오류"),
    NO_EXIST_MEMBER_BY_USERNAME(HttpStatus.BAD_REQUEST, "MEMBER_400_1", "해당 아이디의 유저가 존재하지 않습니다."),
    PASSWORD_MISMATCH_ERROR(HttpStatus.BAD_GATEWAY, "MEMBER_400_1", "비밀번호가 불일치 합니다."),
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
