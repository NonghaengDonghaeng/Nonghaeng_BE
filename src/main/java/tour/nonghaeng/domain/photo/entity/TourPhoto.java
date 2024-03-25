package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.tour.entity.Tour;

@Entity
@Table(name = "TOUR_PHOTOS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TourPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_photo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "img_url")
    private String imgUrl;

    @Builder
    private TourPhoto(Tour tour, String imgUrl) {
        this.tour = tour;
        this.imgUrl = imgUrl;
    }
}
