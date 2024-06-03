package tour.nonghaeng.domain.photo.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.tour.data.Tour;

@Entity
@Table(name = "TOUR_PHOTOS")
@DiscriminatorValue("tour")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TourPhoto extends Photo {

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Builder
    private TourPhoto(Tour tour, Seller seller, String imgUrl) {
        super(imgUrl);
        this.seller = seller;
        this.tour = tour;
    }

    @Override
    public Seller getSeller() {
        return this.seller;
    }

    @Override
    public User getUser() {
        return null;
    }

}
