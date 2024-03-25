package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.room.entity.Room;

@Entity
@Table(name = "ROOM_PHOTOS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomPhoto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_photo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "img_url")
    private String imgUrl;

    @Builder
    private RoomPhoto(Room room, String imgUrl) {
        this.room = room;
        this.imgUrl = imgUrl;
    }
}
