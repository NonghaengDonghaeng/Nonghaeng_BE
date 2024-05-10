package tour.nonghaeng.domain.review.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReviewDetailDto extends ReviewDetailDto {

    private Long roomId;
    private String roomName;
    private String title;
    private String content;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private int likes;
    private RoomSummaryDto roomSummaryDto;
    private List<PhotoInfoDto> photoInfoDtoList;

    @Builder
    private RoomReviewDetailDto(Long roomId, String roomName, String title, String content, String author, LocalDate createDate, int likes, RoomSummaryDto roomSummaryDto, List<PhotoInfoDto> photoInfoDtoList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
        this.likes = likes;
        this.roomSummaryDto = roomSummaryDto;
        this.photoInfoDtoList = photoInfoDtoList;
    }


}
