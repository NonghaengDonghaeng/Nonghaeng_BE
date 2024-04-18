package tour.nonghaeng.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.User;

@Entity
@Table(name = "EXPERIENCE_REVIEWS")
@DiscriminatorValue("experience")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceReview extends Review {

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Builder
    public ExperienceReview(User user, String title, String content, Experience experience) {
        super(user, title, content);
        this.experience = experience;
    }
}
