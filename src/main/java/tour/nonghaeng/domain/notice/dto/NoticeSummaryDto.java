package tour.nonghaeng.domain.notice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.notice.data.Notice;
import tour.nonghaeng.global.infra.dto.SummaryDto;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class NoticeSummaryDto extends SummaryDto {

    private Long noticeId;
    private String title;
    private String author;
    private LocalDateTime createTime;

    @Builder
    private NoticeSummaryDto(Long noticeId, String title, String author, LocalDateTime createTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.author = author;
        this.createTime = createTime;
    }

    public static NoticeSummaryDto toDto(Notice notice) {
        return NoticeSummaryDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .author(notice.getAdmin().getName())
                .createTime(notice.getCreatedAt())
                .build();
    }

    public static Page<NoticeSummaryDto> toDtoPage(Page<Notice> noticePage) {
        return noticePage.map(NoticeSummaryDto::toDto);
    }
}
