package tour.nonghaeng.domain.trip.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.dto.ExpSummaryDto;
import tour.nonghaeng.domain.room.dto.RoomTourSummaryDto;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MainResponseDto {

    private List<RoomTourSummaryDto> roomTourSummaryDtoList;
    private List<ExpSummaryDto> expSummaryDtoList;

    @Builder
    private MainResponseDto(List<RoomTourSummaryDto> roomTourSummaryDtoList, List<ExpSummaryDto> expSummaryDtoList) {
        this.roomTourSummaryDtoList = roomTourSummaryDtoList;
        this.expSummaryDtoList = expSummaryDtoList;
    }
}
