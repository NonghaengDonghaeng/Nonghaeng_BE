package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum TourErrorCode implements BaseErrorCode{
    DUPLICATE_CREATE_TOUR(HttpStatus.BAD_REQUEST, "TOUR_400_1", "이미 여행을 등록한 사업자입니다."),
    NO_TOUR_CONTENT_AT_CURRENT_PAGE(HttpStatus.NOT_FOUND, "TOUR_204_1", "현재 페이지에 여행이 없습니다."),
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
