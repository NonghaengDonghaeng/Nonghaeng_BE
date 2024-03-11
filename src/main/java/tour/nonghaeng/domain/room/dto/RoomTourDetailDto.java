package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomTourDetailDto {

    private String tourName;
    private String tourOneLineIntro;
    private String areaName;

    private String address;
    private String callNumber;
    private String homepageUrl;
    private String restaurant;
    private String parking;
    private String toilet;
    private String amenities;
    private List<RoomSummaryDto> roomSummaryDtoList;

    @Builder
    public RoomTourDetailDto(String tourName, String tourOneLineIntro, String areaName, String address, String callNumber, String homepageUrl, String restaurant, String parking, String toilet, String amenities, List<RoomSummaryDto> roomSummaryDtoList) {
        this.tourName = tourName;
        this.tourOneLineIntro = tourOneLineIntro;
        this.areaName = areaName;
        this.address = address;
        this.callNumber = callNumber;
        this.homepageUrl = homepageUrl;
        this.restaurant = restaurant;
        this.parking = parking;
        this.toilet = toilet;
        this.amenities = amenities;
        this.roomSummaryDtoList = roomSummaryDtoList;
    }

    public static RoomTourDetailDto toDto(Tour tour) {
        return RoomTourDetailDto.builder()
                .tourName(tour.getName())
                .tourOneLineIntro(tour.getOneLineIntro())
                .areaName(tour.getAreaCode().getAreaName())
                .address(tour.getSeller().getAddress())
                .callNumber(tour.getSeller().getCallNumber())
                .homepageUrl(tour.getHomepageUrl())
                .restaurant(tour.getRestaurant())
                .parking(tour.getParking())
                .toilet(tour.getToilet())
                .amenities(tour.getAmenities())
                .build();
    }

    public void addRoomSummaryDtoList(List<RoomSummaryDto> roomSummaryDtoList) {
        this.roomSummaryDtoList = roomSummaryDtoList;
    }
}
