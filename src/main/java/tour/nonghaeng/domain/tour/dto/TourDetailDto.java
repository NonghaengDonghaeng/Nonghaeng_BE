package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.tour.entity.Tour;

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

    @Builder
    public TourDetailDto(String name, String homepageUrl, String introduction, String oneLineIntro, String summary, String restaurant, String parking, String toilet, String amenities, String areaName) {
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
    }

    public static TourDetailDto convert(Tour tour) {
        return TourDetailDto.builder()
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
    }

}
