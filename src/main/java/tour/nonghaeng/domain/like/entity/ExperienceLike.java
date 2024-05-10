package tour.nonghaeng.domain.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.User;

@Entity
@Table(name = "EXPERIENCE_LIKES")
@DiscriminatorValue("experience")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceLike extends Like{

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Builder
    private ExperienceLike(User user, Experience experience) {
        super(user);
        this.experience = experience;
    }
}
