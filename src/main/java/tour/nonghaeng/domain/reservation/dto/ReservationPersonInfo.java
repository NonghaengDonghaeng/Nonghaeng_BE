package tour.nonghaeng.domain.reservation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.Member;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationPersonInfo {

    private String reservationPersonName;
    private String phoneNumber;
    private String email;
    private int point;

    @Builder
    private ReservationPersonInfo(String reservationPersonName, String phoneNumber, String email,int point) {
        this.reservationPersonName = reservationPersonName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.point = point;
    }

    public static ReservationPersonInfo toDto(Member user) {
        return ReservationPersonInfo.builder()
                .reservationPersonName(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .point(user.getPoint())
                .build();
    }
}
