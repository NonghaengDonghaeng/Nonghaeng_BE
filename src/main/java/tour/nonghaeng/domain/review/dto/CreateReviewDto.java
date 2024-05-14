package tour.nonghaeng.domain.review.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateReviewDto {

    private Long id;
    private String title;
    private String content;

    @Builder
    private CreateReviewDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
