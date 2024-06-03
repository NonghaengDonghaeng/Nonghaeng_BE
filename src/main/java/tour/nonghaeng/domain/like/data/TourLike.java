package tour.nonghaeng.domain.like.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.tour.data.Tour;

@Entity
@Table(name = "TOUR_LIKES")
@DiscriminatorValue("tour")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TourLike extends Like {

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Builder
    private TourLike(User user, Tour tour) {
        super(user);
        this.tour = tour;
    }
}
