package tour.nonghaeng.domain.etc.reservation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStateType {
    PAY_RESERVATION("001","결제 완료"),
    WAITING_RESERVATION("002","예약 대기"),   //예약하면 대기중
    CONFIRM_RESERVATION("003","예약 승인"),   //판매자가 승인하면 승인
    COMPLETE_RESERVATION("004","예약 완료"),  //날짜 1일이 지나면 자동으로 완료로 바뀜
    NOT_CONFIRM_RESERVATION("100","예약 미승인"),   //판매자가 미승인하면 미승인
    CANCEL_RESERVATION("101","예약 취소"),    //예약 취소할때
    ;
    @JsonValue
    private final String code;
    private final String name;



}
