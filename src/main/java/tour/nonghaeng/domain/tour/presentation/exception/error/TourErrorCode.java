package tour.nonghaeng.domain.tour.presentation.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.error.BaseErrorCode;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum TourErrorCode implements BaseErrorCode {
    DEFAULT_TOUR_ERROR(HttpStatus.BAD_GATEWAY,"TOUR_400_0","여행 기본 에러"),
    DUPLICATE_CREATE_TOUR_ERROR(HttpStatus.BAD_REQUEST, "TOUR_400_1", "이미 여행을 등록한 사업자입니다."),
    NO_TOUR_CONTENT_AT_CURRENT_PAGE_ERROR(HttpStatus.NOT_FOUND, "TOUR_404_1", "현재 페이지에 여행 없습니다."),
    WRONG_TOUR_ID_ERROR(HttpStatus.NOT_FOUND, "TOUR_404_2", "해당 tourID를 가진 tour가 존재하지 않습니다."),
    NO_REPRESENT_PHOTO_ERROR(HttpStatus.BAD_GATEWAY,"TOUR_101_1","대표사진이 없습니다.")
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
