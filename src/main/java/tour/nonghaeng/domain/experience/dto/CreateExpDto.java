package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.enums.experience.ExperienceType;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.infra.dto.CreateDto;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class CreateExpDto extends CreateDto {
    private ExperienceType experienceType;
    private String experienceName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int minParticipant;
    private int maxParticipant;
    private int price;
    private int durationHours;
    private String checkPoint;
    private String detailIntroduction;
    private String summary;
    private String supplies;
    private String precautions;
    private List<AddExpRoundDto> expRoundDtoList;

    public CreateExpDto(ExperienceType experienceType, String experienceName, LocalDate startDate, LocalDate endDate, int minParticipant, int maxParticipant, int price, int durationHours, String checkPoint, String detailIntroduction, String summary, String supplies, String precautions, List<AddExpRoundDto> expRoundDtoList) {
        this.experienceType = experienceType;
        this.experienceName = experienceName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minParticipant = minParticipant;
        this.maxParticipant = maxParticipant;
        this.price = price;
        this.durationHours = durationHours;
        this.checkPoint = checkPoint;
        this.detailIntroduction = detailIntroduction;
        this.summary = summary;
        this.supplies = supplies;
        this.precautions = precautions;
        this.expRoundDtoList = expRoundDtoList;
    }

    public Experience toEntity(Tour tour) {
        return Experience.builder()
                .tour(tour)
                .experienceType(this.experienceType)
                .experienceName(this.experienceName)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .minParticipant(this.minParticipant)
                .maxParticipant(this.maxParticipant)
                .price(this.price)
                .durationHours(this.durationHours)
                .checkPoint(this.checkPoint)
                .detailIntroduction(this.detailIntroduction)
                .summary(this.summary)
                .supplies(this.supplies)
                .precautions(this.precautions)
                .build();
    }
}
