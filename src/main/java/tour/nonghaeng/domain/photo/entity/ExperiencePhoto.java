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
public class ExperiencePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_photo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Column(name = "img_url")
    private String imgUrl;


    @Builder
    private ExperiencePhoto(Experience experience, String imgUrl) {
        this.experience = experience;
        this.imgUrl = imgUrl;
    }
}
