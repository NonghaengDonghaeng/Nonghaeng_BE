package tour.nonghaeng.global.infra.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode implements BaseErrorCode{

    ALL_NOT_FOUND(HttpStatus.NOT_FOUND,"GLOBAL_404_1","전체 오류"),

    //enum 오류코드
    NO_MATCH_AREA_CODE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 지역번호가 없습니다."),
    NO_MATCH_BANK_CODE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 은행코드가 없습니다."),
    NO_MATCH_EXPERIENCE_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 체험 타입이 없습니다."),
    NO_MATCH_LIKE_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 좋아요 타입이 없습니다."),
    NO_MATCH_PHOTO_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 사진 타입이 없습니다."),
    NO_MATCH_RESERVATION_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 예약 타입이 없습니다."),
    NO_MATCH_REVIEW_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 후기 타입이 없습니다."),
    NO_MATCH_ROLE_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 role 타입이 없습니다."),
    NO_MATCH_ROOM_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 숙소 타입이 없습니다."),
    NO_MATCH_TOUR_TYPE(HttpStatus.NOT_FOUND,"GLOBAL_404_1","일치하는 관광 타입이 없습니다."),

    CODE_IS_NULL_ERROR(HttpStatus.NOT_FOUND, "GLOBAL_404_1", "들어온 code가 Null 입니다."),

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
