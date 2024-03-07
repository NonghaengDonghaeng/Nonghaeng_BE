package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TourSummaryDto(
        Long tourId,
        String name,
        String areaCode,
        String tourType,
        int countExperience,
        int countAccommodation,
        String oneLineIntro
) {
}
