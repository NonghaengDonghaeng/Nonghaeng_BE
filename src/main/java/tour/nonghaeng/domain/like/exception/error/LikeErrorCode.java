package tour.nonghaeng.domain.like.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;
import tour.nonghaeng.global.exception.error.BaseErrorCode;

@AllArgsConstructor
@Getter
public enum LikeErrorCode implements BaseErrorCode {

    DEFAULT_LIKE_ERROR(HttpStatus.BAD_GATEWAY, "like_400_0", "좋아요 기본 오류"),

    ALREADY_EXISTED_LIKE_ERROR(HttpStatus.BAD_GATEWAY, "like_400_0", "좋아요를 이미 눌렀습니다."),

    WRONG_LIKE_TYPE(HttpStatus.BAD_GATEWAY, "like_400_0", "좋아요 타입이 잘못 되었습니다."),
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
