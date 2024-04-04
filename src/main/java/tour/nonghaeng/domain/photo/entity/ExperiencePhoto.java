package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.Seller;

@Entity
@Table(name = "EXPERIENCE_PHOTOS")
@DiscriminatorValue("experience")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperiencePhoto extends Photo{

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Builder
    private ExperiencePhoto(Experience experience, Seller seller, String imgUrl) {
        super(seller, imgUrl);
        this.experience = experience;
    }
}
