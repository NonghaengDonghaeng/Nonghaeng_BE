package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class TourDetailDto {
    private String name;
    private String homepageUrl;
    private String introduction;
    private String oneLineIntro;
    private String summary;
    private String restaurant;
    private String parking;
    private String toilet;
    private String amenities;
    private String areaName;
    private List<RoomSummary> roomSummaryList;
    private List<ExpSummary> expSummaryList;
    private List<PhotoInfoDto> photoInfoDtoList;


    @Builder
    public TourDetailDto(String name, String homepageUrl, String introduction, String oneLineIntro, String summary, String restaurant, String parking, String toilet, String amenities, String areaName, List<RoomSummary> roomSummaryList, List<ExpSummary> expSummaryList, List<PhotoInfoDto> photoInfoDtoList) {
        this.name = name;
        this.homepageUrl = homepageUrl;
        this.introduction = introduction;
        this.oneLineIntro = oneLineIntro;
        this.summary = summary;
        this.restaurant = restaurant;
        this.parking = parking;
        this.toilet = toilet;
        this.amenities = amenities;
        this.areaName = areaName;
        this.roomSummaryList = roomSummaryList;
        this.expSummaryList = expSummaryList;
        this.photoInfoDtoList = photoInfoDtoList;
    }

    public static TourDetailDto toDto(Tour tour) {

        TourDetailDto tourDetailDto = TourDetailDto.builder()
                .name(tour.getName())
                .homepageUrl(tour.getHomepageUrl())
                .introduction(tour.getIntroduction())
                .oneLineIntro(tour.getOneLineIntro())
                .summary(tour.getSummary())
                .restaurant(tour.getRestaurant())
                .parking(tour.getParking())
                .toilet(tour.getToilet())
                .amenities(tour.getAmenities())
                .areaName(tour.getAreaCode().getAreaName())
                .build();
        tourDetailDto.addRoomSummaryList(tour);
        tourDetailDto.addExpSummaryList(tour);

        return tourDetailDto;

    }
    private void addRoomSummaryList(Tour tour) {

        this.roomSummaryList = tour.getRooms().stream()
                .map(room -> new RoomSummary(room.getId(), room.getRoomName(), room.getPricePeak()))
                .toList();

    }

    private void addExpSummaryList(Tour tour) {
        this.expSummaryList = tour.getExperiences().stream()
                .map(experience -> new ExpSummary(experience.getId(), experience.getExperienceName(), experience.getPrice()))
                .toList();
    }

    public void addPhotoInfoDtoList(List<PhotoInfoDto> photoInfoDtoList) {
        this.photoInfoDtoList = photoInfoDtoList;
    }

    @AllArgsConstructor
    @Getter
    public class RoomSummary {
        private Long roomId;
        private String roomName;
        private int price;
    }

    @AllArgsConstructor
    @Getter
    public class ExpSummary {
        private Long expId;
        private String expName;
        private int price;
    }



}
