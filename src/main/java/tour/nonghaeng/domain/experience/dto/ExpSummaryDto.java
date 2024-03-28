package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpSummaryDto {

    private Long experienceId;
    private String experienceName;
    private int price;
    private int minParticipant;
    private int maxParticipant;
    private String areaName;
    private String tourName;
    private String summary;
    private PhotoInfoDto photoInfoDto;

    @Builder
    public ExpSummaryDto(Long experienceId, String experienceName, int price, int minParticipant, int maxParticipant, String areaName, String tourName, String summary, PhotoInfoDto photoInfoDto) {
        this.experienceId = experienceId;
        this.experienceName = experienceName;
        this.price = price;
        this.minParticipant = minParticipant;
        this.maxParticipant = maxParticipant;
        this.areaName = areaName;
        this.tourName = tourName;
        this.summary = summary;
        this.photoInfoDto = photoInfoDto;
    }

    public static Page<ExpSummaryDto> toPageDto(Page<Experience> expPage) {
        return expPage.map(exp -> ExpSummaryDto.builder()
                .experienceId(exp.getId())
                .experienceName(exp.getExperienceName())
                .price(exp.getPrice())
                .minParticipant(exp.getMinParticipant())
                .maxParticipant(exp.getMaxParticipant())
                .areaName(exp.getTour().getAreaCode().getAreaName())
                .tourName(exp.getTour().getName())
                .summary(exp.getSummary())
                .photoInfoDto(PhotoInfoDto.toDto(exp.findRepresentPhoto().get()))
                .build());
    }
}
