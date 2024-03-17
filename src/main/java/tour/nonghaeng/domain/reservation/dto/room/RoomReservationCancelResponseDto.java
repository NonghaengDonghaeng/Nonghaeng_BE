package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationCancelResponseDto {

    private String cancelPolicy;            //정책이름
    private double percent;                 //취소 수수료
    private int originPayPoint;             //원래 지불한 가격
    private int payBackPoint;               //환급양
    private int remainPoint;                //내 남은 포인트
    private String roomName;          //취소한 체험이름
    private Long roomReservationId;         //숙소예약 id

    @Builder
    private RoomReservationCancelResponseDto(String cancelPolicy, double percent, int originPayPoint, int payBackPoint, int remainPoint, String roomName, Long roomReservationId) {
        this.cancelPolicy = cancelPolicy;
        this.percent = percent;
        this.originPayPoint = originPayPoint;
        this.payBackPoint = payBackPoint;
        this.remainPoint = remainPoint;
        this.roomName = roomName;
        this.roomReservationId = roomReservationId;
    }

    public static RoomReservationCancelResponseDto toDto(RoomReservation roomReservation, CancelPolicy cancelPolicy) {
        return RoomReservationCancelResponseDto.builder()
                .cancelPolicy(cancelPolicy.getName())
                .percent(cancelPolicy.getPercent())
                .originPayPoint(roomReservation.getPrice())
                .payBackPoint((int) (roomReservation.getPrice() * (1 - cancelPolicy.getPercent())))
                .remainPoint(roomReservation.getUser().getPoint())
                .roomName(roomReservation.getRoom().getRoomName())
                .roomReservationId(roomReservation.getId())
                .build();
    }
}
