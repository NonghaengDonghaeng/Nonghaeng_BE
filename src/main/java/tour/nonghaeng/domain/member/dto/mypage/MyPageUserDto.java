package tour.nonghaeng.domain.member.dto.mypage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.reservation.dto.ReservationUserSummaryDto;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyPageUserDto {

    private String name;
    private String email;
    private String number;
    private int point;
    private List<? extends ReservationUserSummaryDto> reservations;

    @Builder
    private MyPageUserDto(String name, String email, String number, int point, List<? extends ReservationUserSummaryDto> reservations) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.point = point;
        this.reservations = reservations;
    }
}
