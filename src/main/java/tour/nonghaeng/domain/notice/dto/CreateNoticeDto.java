package tour.nonghaeng.domain.notice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.Admin;
import tour.nonghaeng.domain.notice.data.Notice;
import tour.nonghaeng.global.infra.dto.CreateDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateNoticeDto extends CreateDto {

    private String title;
    private String content;
    private boolean important;


    public Notice toEntity(Admin admin) {
        return Notice.builder()
                .admin(admin)
                .title(title)
                .content(content)
                .important(important)
                .build();
    }
}
