package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.room.entity.Room;

@Entity
@Table(name = "ROOM_PHOTOS")
@DiscriminatorValue("room_photo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomPhoto extends Photo{

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    private RoomPhoto(Room room, String imgUrl) {
        super(imgUrl);
        this.room = room;
    }
}
