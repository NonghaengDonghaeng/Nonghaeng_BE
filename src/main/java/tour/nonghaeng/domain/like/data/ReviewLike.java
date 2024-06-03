package tour.nonghaeng.domain.like.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.review.data.Review;

@Entity
@Table(name = "REVIEW_LIKES")
@DiscriminatorValue("review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewLike extends Like {

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    private ReviewLike(User user, Review review) {
        super(user);
        this.review = review;
    }
}
