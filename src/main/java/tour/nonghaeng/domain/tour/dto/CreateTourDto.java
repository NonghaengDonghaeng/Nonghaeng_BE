package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.RequiredArgsConstructor;
import tour.nonghaeng.domain.etc.tourType.TourType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.validation.TourValidation;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateTourDto(
        TourType tourType,
        String tourName,
        String homepageUrl,
        String introduction,
        String oneLineIntro,
        String summary,
        String restaurant,
        String parking,
        String toilet,
        String amenities
) {
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
