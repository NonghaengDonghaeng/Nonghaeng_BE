package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.Review;


@Entity
@Table(name = "REVIEW_PHOTOS")
@DiscriminatorValue("review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewPhoto extends Photo {

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    private ReviewPhoto(Review review, User user, String imgUrl) {
        super(imgUrl);
        this.review = review;
        this.user = user;
    }

    @Override
    public Seller getSeller() {
        return null;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
