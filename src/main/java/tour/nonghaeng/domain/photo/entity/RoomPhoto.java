package tour.nonghaeng.domain.photo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.room.entity.Room;

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
