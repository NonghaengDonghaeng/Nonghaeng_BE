package tour.nonghaeng.domain.trip.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.dto.ExpSummaryDto;
import tour.nonghaeng.domain.room.dto.RoomTourSummaryDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TripResponseDto {

    private List<TourSummaryDto> tourSummaryDtoList;
    private List<RoomTourSummaryDto> roomTourSummaryDtoList;
    private List<ExpSummaryDto> expSummaryDtoList;

    @Builder
    private TripResponseDto(List<TourSummaryDto> tourSummaryDtoList, List<RoomTourSummaryDto> roomTourSummaryDtoList, List<ExpSummaryDto> expSummaryDtoList) {
        this.tourSummaryDtoList = tourSummaryDtoList;
        this.roomTourSummaryDtoList = roomTourSummaryDtoList;
        this.expSummaryDtoList = expSummaryDtoList;
    }


}
