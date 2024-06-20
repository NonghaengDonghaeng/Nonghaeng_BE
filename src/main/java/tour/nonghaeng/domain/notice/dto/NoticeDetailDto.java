package tour.nonghaeng.domain.notice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.notice.data.Notice;
import tour.nonghaeng.global.infra.dto.DetailDto;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class NoticeDetailDto extends DetailDto {

    private Long noticeId;
    private String title;
    private String content;
    private String author;
    private boolean important;
    private LocalDateTime createTime;

    @Builder
    public NoticeDetailDto(Long noticeId, String title, String content, String author, boolean important, LocalDateTime createTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.important = important;
        this.createTime = createTime;
    }

    public static NoticeDetailDto toDto(Notice notice) {
        return NoticeDetailDto.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .author(notice.getAdmin().getName())
                .important(notice.isImportant())
                .createTime(notice.getCreatedAt())
                .build();
    }
}
