package tour.nonghaeng.domain.review.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.experience.dto.ExpSummaryDto;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReviewDetailDto extends ReviewDetailDto {

    private Long expId;
    private String expName;
    private String title;
    private String content;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    private int likes;
    private ExpSummaryDto expSummaryDto;
    private List<PhotoInfoDto> photoInfoDtoList;

    @Builder
    private ExpReviewDetailDto(Long expId, String expName,String title, String content, String author, LocalDate createDate, int likes, ExpSummaryDto expSummaryDto,List<PhotoInfoDto> photoInfoDtoList) {
        this.expId = expId;
        this.expName = expName;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDate = createDate;
        this.likes = likes;
        this.expSummaryDto = expSummaryDto;
        this.photoInfoDtoList = photoInfoDtoList;
    }
}
