package tour.nonghaeng.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.ReviewPhoto;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "REVIEWS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public abstract class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //추후에 onetone 관계로 바꾸고 주인을 Reservation으로 설정하기
    @ManyToOne
    @JoinColumn(name="reservation_id")
    private Reservation reservation;

    private String title;

    private String content;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();


    public Review(User user, Reservation reservation, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.reservation = reservation;
    }

    public Optional<Photo> findRepresentPhoto() {

        for (ReviewPhoto rp : this.reviewPhotos) {
            if (rp.isRepresentative()) {
                return Optional.ofNullable(rp);
            }
        }

        return Optional.ofNullable(null);
    }
    public abstract ReviewSummaryDto toReviewSummaryDto();

    public abstract ReviewDetailDto toReviewDetailDto();

}
