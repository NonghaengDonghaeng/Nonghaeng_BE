package tour.nonghaeng.domain.tour.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.BaseTimeEntity;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.tour.TourType;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.like.data.TourLike;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.photo.data.Photo;
import tour.nonghaeng.domain.photo.data.TourPhoto;
import tour.nonghaeng.domain.room.data.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "TOURS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tour extends BaseTimeEntity {

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

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourPhoto> tourPhotos = new ArrayList<>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourLike> tourLikes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AreaCode areaCode;

    @Enumerated(EnumType.STRING)
    private TourType tourType;

    //TODO: 추후에는 이게 겹치지 않도록 Unique 설정해야 함
    private String name;

    private String homepageUrl;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String oneLineIntro;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String restaurant;

    private String parking;

    private String toilet;

    private String amenities;

    private int likes;

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
        this.likes = 0;
    }

    public Optional<Photo> findRepresentPhoto() {

        for (TourPhoto tp : this.tourPhotos) {
            if (tp.isRepresentative()) {
                return Optional.ofNullable(tp);
            }
        }

        return Optional.ofNullable(null);
    }

    public void plusLikes(){
        this.likes += 1;
    }

    public void minusLikes(){
        this.likes -= 1;
    }
}
