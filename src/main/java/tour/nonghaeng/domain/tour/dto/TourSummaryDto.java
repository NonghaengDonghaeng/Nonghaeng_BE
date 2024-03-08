package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Builder
@Getter
public class TourSummaryDto{

    Long tourId;
    String name;
    String areaName;
    String tourType;
    int countExperience;
    int countRoom;
    String oneLineIntro;

    public static Page<TourSummaryDto> toEntity(Page<Tour> tourPage) {

        return tourPage.map(tour -> TourSummaryDto.builder()
                .tourId(tour.getId())
                .name(tour.getName())
                .areaName(tour.getAreaCode().getAreaName())
                .tourType(tour.getTourType().getName())
                .countExperience(tour.getExperiences().size())
                .countRoom(tour.getRooms().size())
                .oneLineIntro(tour.getOneLineIntro())
                .build()
        );
    }


}
