package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.tour.entity.Tour;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TourSummaryDto{

    private Long tourId;
    private String name;
    private String areaName;
    private String tourType;
    private int countExperience;
    private int countRoom;
    private String oneLineIntro;

    @Builder
    public TourSummaryDto(Long tourId, String name, String areaName, String tourType, int countExperience, int countRoom, String oneLineIntro) {
        this.tourId = tourId;
        this.name = name;
        this.areaName = areaName;
        this.tourType = tourType;
        this.countExperience = countExperience;
        this.countRoom = countRoom;
        this.oneLineIntro = oneLineIntro;
    }

    public static Page<TourSummaryDto> toPageDto(Page<Tour> tourPage) {

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
