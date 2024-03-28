package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.tour.entity.Tour;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomTourSummaryDto {

    private Long tourId;
    private String tourName;
    private String areaName;
    private String roomTypeName;
    private String oneLineIntro;
    private int minPrice;
    private int maxPrice;
    private PhotoInfoDto photoInfoDto;

    @Builder
    public RoomTourSummaryDto(Long tourId, String tourName, String areaName, String roomTypeName, String oneLineIntro, int minPrice, int maxPrice, PhotoInfoDto photoInfoDto) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.areaName = areaName;
        this.roomTypeName = roomTypeName;
        this.oneLineIntro = oneLineIntro;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.photoInfoDto = photoInfoDto;
    }

    public static Page<RoomTourSummaryDto> toPageDto(Page<Tour> tourPage) {

        return tourPage.map(tour -> RoomTourSummaryDto.builder()
                .tourId(tour.getId())
                .tourName(tour.getName())
                .areaName(tour.getAreaCode().getAreaName())
                .roomTypeName(tour.getRooms().get(0).getRoomType().getName())
                .oneLineIntro(tour.getOneLineIntro())
                .build()
        );
    }

    public static RoomTourSummaryDto toDto(Tour tour, int minPrice, int maxPrice) {

        return RoomTourSummaryDto.builder()
                .tourId(tour.getId())
                .tourName(tour.getName())
                .areaName(tour.getAreaCode().getAreaName())
                .roomTypeName(tour.getRooms().get(0).getRoomType().getName())
                .oneLineIntro(tour.getOneLineIntro())
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .photoInfoDto(PhotoInfoDto.toDto(tour.findRepresentPhoto().get()))
                .build();
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}
