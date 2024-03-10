package tour.nonghaeng.domain.tour.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.tour.TourType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.entity.Room;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TOURS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AreaCode areaCode;

    @Enumerated(EnumType.STRING)
    private TourType tourType;

    private String name;

    private String homepageUrl;

    private String introduction;

    private String oneLineIntro;

    private String summary;

    private String restaurant;

    private String parking;

    private String toilet;

    private String amenities;

    @Builder
    public Tour(Seller seller, TourType tourType, String name, String homepageUrl, String introduction, String oneLineIntro, String summary, String restaurant, String parking, String toilet, String amenities) {
        this.seller = seller;
        this.areaCode = seller.getAreaCode();
        this.tourType = tourType;
        this.name = name;
        this.homepageUrl = homepageUrl;
        this.introduction = introduction;
        this.oneLineIntro = oneLineIntro;
        this.summary = summary;
        this.restaurant = restaurant;
        this.parking = parking;
        this.toilet = toilet;
        this.amenities = amenities;
    }

}
