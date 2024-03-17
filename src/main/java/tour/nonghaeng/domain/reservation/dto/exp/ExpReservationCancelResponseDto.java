package tour.nonghaeng.domain.reservation.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReservationCancelResponseDto {

    private String cancelPolicy;            //정책이름
    private double percent;                 //취소 수수료
    private int originPayPoint;             //원래 지불한 가격
    private int payBackPoint;               //환급양
    private int remainPoint;                //내 남은 포인트
    private String experienceName;          //취소한 체험이름
    private Long experienceReservationId;   //체험예약 id


    @Builder
    public ExpReservationCancelResponseDto(String cancelPolicy, double percent, int originPayPoint, int payBackPoint, int remainPoint, String experienceName, Long experienceReservationId) {
        this.cancelPolicy = cancelPolicy;
        this.percent = percent;
        this.originPayPoint = originPayPoint;
        this.payBackPoint = payBackPoint;
        this.remainPoint = remainPoint;
        this.experienceName = experienceName;
        this.experienceReservationId = experienceReservationId;
    }

    public static ExpReservationCancelResponseDto toDto(ExperienceReservation experienceReservation, CancelPolicy cancelPolicy ) {
        return ExpReservationCancelResponseDto.builder()
                .cancelPolicy(cancelPolicy.getName())
                .percent(cancelPolicy.getPercent())
                .originPayPoint(experienceReservation.getPrice())
                .payBackPoint((int) (experienceReservation.getPrice() * (1 - cancelPolicy.getPercent())))
                .remainPoint(experienceReservation.getUser().getPoint())
                .experienceName(experienceReservation.getExperience().getExperienceName())
                .experienceReservationId(experienceReservation.getId())
                .build();
    }
}
