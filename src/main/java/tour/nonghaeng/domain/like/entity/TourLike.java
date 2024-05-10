package tour.nonghaeng.domain.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.tour.entity.Tour;

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
