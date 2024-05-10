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
    private int likes;
    private PhotoInfoDto photoInfoDto;

    @Builder
    public ExpSummaryDto(Long experienceId, String experienceName, int price, int minParticipant, int maxParticipant, String areaName, String tourName, String summary, int likes, PhotoInfoDto photoInfoDto) {

        this.experienceId = experienceId;
        this.experienceName = experienceName;
        this.price = price;
        this.minParticipant = minParticipant;
        this.maxParticipant = maxParticipant;
        this.areaName = areaName;
        this.tourName = tourName;
        this.summary = summary;
        this.likes = likes;
        this.photoInfoDto = photoInfoDto;
    }

    public static ExpSummaryDto toDto(Experience experience) {
        return ExpSummaryDto.builder()
                .experienceId(experience.getId())
                .experienceName(experience.getExperienceName())
                .price(experience.getPrice())
                .minParticipant(experience.getMinParticipant())
                .maxParticipant(experience.getMaxParticipant())
                .areaName(experience.getTour().getAreaCode().getAreaName())
                .tourName(experience.getTour().getName())
                .summary(experience.getSummary())
                .likes(experience.getExperienceLikes().size())
                .photoInfoDto(experience.findRepresentPhoto().isPresent() ?
                        PhotoInfoDto.toDto(experience.findRepresentPhoto().get()) : null)
                .build();
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
                .likes(exp.getExperienceLikes().size())
                .photoInfoDto(exp.findRepresentPhoto().isPresent() ?
                        PhotoInfoDto.toDto(exp.findRepresentPhoto().get()) : null)
                .build());
    }
}
