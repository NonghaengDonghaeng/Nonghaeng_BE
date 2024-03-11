package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum RoomErrorCode implements BaseErrorCode{
    DEFAULT_ROOM_ERROR(HttpStatus.BAD_GATEWAY,"ROOM_400_0","숙소 기본 오류"),
    NO_TOUR_ROOM_CONTENT_AT_CURRENT_PAGE_ERROR(HttpStatus.NOT_FOUND, "ROOM_100_1", "현재 페이지에 숙박리스트가 없습니다."),
    NO_ROOM_AT_THIS_CONDITION(HttpStatus.BAD_GATEWAY, "ROOM_101_1", "이 조건의 충족하는 방이 없습니다."),
    NO_ROOM_IN_THIS_TOUR(HttpStatus.BAD_GATEWAY, "ROOM_101_1", "이 여행지에는 숙소가 존재하지 않습니다."),
    PAST_DATE_FOR_ROOM_LIST_REQUEST_ERROR(HttpStatus.BAD_GATEWAY, "ROOM_102_3", "지난 날짜의 방들을 조회할 수 없습니다."),
    CLOSE_DATE_FOR_ROOM_LIST_REQUEST_ERROR(HttpStatus.BAD_GATEWAY, "ROOM_102_4", "영업종료된 날짜입니다."),
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
