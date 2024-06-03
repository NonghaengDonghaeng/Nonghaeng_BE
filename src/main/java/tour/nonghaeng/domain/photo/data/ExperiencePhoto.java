package tour.nonghaeng.domain.photo.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;

@Entity
@Table(name = "EXPERIENCE_PHOTOS")
@DiscriminatorValue("experience")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperiencePhoto extends Photo{

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Builder
    private ExperiencePhoto(Experience experience, Seller seller, String imgUrl) {
        super(imgUrl);
        this.seller = seller;
        this.experience = experience;
    }

    @Override
    public Seller getSeller() {
        return this.seller;
    }

    @Override
    public User getUser() {
        return null;
    }
}
