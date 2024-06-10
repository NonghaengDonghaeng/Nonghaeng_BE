package tour.nonghaeng.domain.member.dto.mypage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.member.dto.MyPageDto;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyPageUserDto extends MyPageDto {

    private String name;
    private String email;
    private String number;
    private int point;
    private Page<? extends ReservationSummaryDto> reservationPage;
    private Page<? extends ReviewSummaryDto> reviewPage;

    @Builder
    private MyPageUserDto(String name, String email, String number, int point, Page<? extends ReservationSummaryDto> reservationPage, Page<? extends ReviewSummaryDto> reviewPage) {

        this.name = name;
        this.email = email;
        this.number = number;
        this.point = point;
        this.reservationPage = reservationPage;
        this.reviewPage = reviewPage;
    }
}
