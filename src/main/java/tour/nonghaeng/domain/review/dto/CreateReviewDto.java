package tour.nonghaeng.domain.review.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.dto.CreateDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateReviewDto extends CreateDto {

    private Long id;    //entity id
    private String title;
    private String content;
    private Long reservationId;

    @Builder
    private CreateReviewDto(Long id, String title, String content,Long reservationId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.reservationId = reservationId;
    }
}
