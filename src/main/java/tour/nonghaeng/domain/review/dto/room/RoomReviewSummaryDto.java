package tour.nonghaeng.domain.review.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReviewSummaryDto extends ReviewSummaryDto {

    private Long reviewId;
    private String roomName;
    private String title;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private String type;
    private PhotoInfoDto photoInfoDto;

    @Builder
    private RoomReviewSummaryDto(Long reviewId,String roomName, String title,String author, LocalDate createDate,String type,PhotoInfoDto photoInfoDto) {
        this.reviewId = reviewId;
        this.roomName = roomName;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.type = type;
        this.photoInfoDto = photoInfoDto;
    }
}
