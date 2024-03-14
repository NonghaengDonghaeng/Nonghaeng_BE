package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum ReservationErrorCode implements BaseErrorCode {
    DEFAULT_RESERVATION_ERROR(HttpStatus.BAD_GATEWAY,"RES_400_0","예약 기본 오류"),

    //해당 페이지에 예약이 없을시 에러코드
    NO_RESERVATION_CONTENT_AT_CURRENT_PAGE_ERROR(HttpStatus.NOT_FOUND, "RES_105_1", "현재 페이지에 예약이 없습니다."),

    //예약 승인 API 사용시 예약 대기중이 아닐때
    NOT_WAITING_RESERVATION_STATE(HttpStatus.BAD_GATEWAY, "RES_009_1", "예약 대기중인 체험예약이 아닙니다."),

    //예약 취소 API 사용시 예약이 이미 취소되거나 완료된 상태일 때
    CANT_CANCEL_RESERVATION_STATE(HttpStatus.BAD_GATEWAY, "RES_009_1", "해당 예약을 취소할 수 없습니다."),

    //이 예약에 대한 판매자가 아닐경우 에로코드
    NO_OWNER_AUTHORIZATION_ERROR(HttpStatus.BAD_GATEWAY, "RES_000_2", "해당 체험 예약에 대한 소유자가 아닙니다."),
    //체험예약
    NO_EXIST_EXPERIENCE_RESERVATION_BY_ID(HttpStatus.BAD_GATEWAY, "RES_100_0", "해당 아이디의 체험예약이 존재하지 않습니다."),
    WRONG_FINAL_PRICE_ERROR(HttpStatus.BAD_GATEWAY, "RES_102_1", "최종가격이 잘못되었습니다."),
    EXCEEDED_PARTICIPANT(HttpStatus.BAD_GATEWAY, "RES_101_1", "남은 참여자수보다 초과했습니다."),
    NOT_ENOUGH_POINT_ERROR(HttpStatus.BAD_GATEWAY, "RES_200_1", "잔여 포인트가 부족합니다."),
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
