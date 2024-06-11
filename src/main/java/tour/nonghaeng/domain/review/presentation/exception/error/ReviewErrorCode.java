package tour.nonghaeng.domain.review.presentation.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum ReviewErrorCode implements BaseErrorCode {

    DEFAULT_REVIEW_ERROR(HttpStatus.BAD_GATEWAY,"review_400_0","리뷰 기본 오류"),
    NO_EXIST_REVIEW_ID(HttpStatus.BAD_GATEWAY,"review_400_1","리뷰아이디가 존재하지 않습니다."),
    NO_OWNER_AUTHORIZATION_ERROR(HttpStatus.BAD_GATEWAY, "review_000_2", "해당 리뷰에 대한 소유자가 아닙니다."),
    WRONG_DTYPE_ERROR(HttpStatus.BAD_GATEWAY,"review_100_1","dtype 에러"),

    ALREADY_EXISTED_REVIEW(HttpStatus.BAD_GATEWAY,"review_400_1","이미 작성된 리뷰가 있습니다."),
    NO_REVIEW_CONTENT_AT_CURRENT_PAGE_ERROR(HttpStatus.NOT_FOUND, "review_105_1", "현재 페이지에 리뷰가 없습니다."),
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
