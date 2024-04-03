package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHOTOS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    private boolean representative;

    //Seller를 연관관계를 맺을지 고민중

    public Photo(String imgUrl) {
        this.imgUrl = imgUrl;
        this.representative = false;
    }

    public void onRepresentative(){
        this.representative = true;
    }

    public void offRepresentative() {
        this.representative = false;
    }
}
