package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum SellerErrorCode implements BaseErrorCode{
    DEFAULT_SELLER_ERROR(HttpStatus.BAD_GATEWAY,"SELLER_400_0","seller 기본 오류"),
    NO_EXIST_SELLER_BY_USERNAME(HttpStatus.BAD_REQUEST, "USER_400_1", "해당 아이디의 유저가 존재하지 않습니다."),
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
