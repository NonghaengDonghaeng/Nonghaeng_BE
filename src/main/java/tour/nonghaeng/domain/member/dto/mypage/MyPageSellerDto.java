package tour.nonghaeng.domain.member.dto.mypage;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.dto.MyPageDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyPageSellerDto extends MyPageDto {

    private String name;
    private String email;
    private String number;
    private int point;

    @Builder
    private MyPageSellerDto(String name, String email, String number, int point) {

        this.name = name;
        this.email = email;
        this.number = number;
        this.point = point;
    }
}
