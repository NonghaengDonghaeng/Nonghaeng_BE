package tour.nonghaeng.domain.review.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReviewSummaryDto extends ReviewSummaryDto {

    private Long reviewId;
    private String expName;
    private String title;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private String type;

    @Builder
    private ExpReviewSummaryDto(Long reviewId,String expName, String title, String author, LocalDate createDate,String type) {
        this.reviewId = reviewId;
        this.expName = expName;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.type = type;
    }
}
