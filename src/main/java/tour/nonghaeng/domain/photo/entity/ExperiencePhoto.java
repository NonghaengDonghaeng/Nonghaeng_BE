package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.entity.Experience;

@Entity
@Table(name = "EXPERIENCE_PHOTOS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperiencePhoto extends Photo{

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Builder
    private ExperiencePhoto(Experience experience, String imgUrl) {
        super(imgUrl);
        this.experience = experience;
    }
}
