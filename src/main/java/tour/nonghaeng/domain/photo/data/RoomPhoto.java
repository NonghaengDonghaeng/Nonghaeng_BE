package tour.nonghaeng.domain.photo.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.room.data.Room;

@Entity
@Table(name = "ROOM_PHOTOS")
@DiscriminatorValue("room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomPhoto extends Photo{

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Builder
    private RoomPhoto(Room room, Seller seller, String imgUrl) {
        super(imgUrl);
        this.seller = seller;
        this.room = room;
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
