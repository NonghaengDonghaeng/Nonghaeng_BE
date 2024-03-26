package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.tour.entity.Tour;

@Entity
@Table(name = "TOUR_PHOTOS")
@DiscriminatorValue("tour_photo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TourPhoto extends Photo {

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Builder
    private TourPhoto(Tour tour,String imgUrl) {
        super(imgUrl);
        this.tour = tour;
    }

}
