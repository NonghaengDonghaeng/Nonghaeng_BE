package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum ReservationErrorCode implements BaseErrorCode {
    DEFAULT_RESERVATION_ERROR(HttpStatus.BAD_GATEWAY,"RES_400_0","예약 기본 오류"),

    //체험예약
    NO_EXIST_EXPERIENCE_RESERVATION_BY_ID(HttpStatus.BAD_GATEWAY, "RES_100_0", "해당 아이디의 체험예약이 존재하지 않습니다."),
    WRONG_FINAL_PRICE_ERROR(HttpStatus.BAD_GATEWAY, "RES_102_1", "최종가격이 잘못되었습니다."),
    EXCEEDED_PARTICIPANT(HttpStatus.BAD_GATEWAY, "RES_101_1", "남은 참여자수보다 초과했습니다."),
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
