package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import tour.nonghaeng.domain.etc.experience.ExperienceType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateExpDto(
        ExperienceType experienceType,
        String experienceName,
        LocalDate startDate,
        LocalDate endDate,
        int minParticipant,
        int maxParticipant,
        int price,
        int durationHours,
        String checkPoint,
        String detailIntroduction,
        String summary,
        String supplies,
        String precautions,
        List<AddExpRoundDto> expRoundDtoList
) {
    public Experience toEntity(Seller seller, Tour tour) {
        return Experience.builder()
                .seller(seller)
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
