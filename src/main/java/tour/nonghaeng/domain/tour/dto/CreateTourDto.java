package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.enums.tour.TourType;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.infra.dto.CreateDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateTourDto extends CreateDto {

    private TourType tourType;
    private String tourName;
    private String homepageUrl;
    private String introduction;
    private String oneLineIntro;
    private String summary;
    private String restaurant;
    private String parking;
    private String toilet;
    private String amenities;


    public Tour toEntity(Seller seller) {
        return Tour.builder()
                .seller(seller)
                .tourType(this.tourType)
                .name(this.tourName)
                .homepageUrl(this.homepageUrl)
                .introduction(this.introduction)
                .oneLineIntro(this.oneLineIntro)
                .summary(this.summary)
                .restaurant(this.restaurant)
                .parking(this.parking)
                .toilet(this.toilet)
                .amenities(this.amenities)
                .build();
    }

}
