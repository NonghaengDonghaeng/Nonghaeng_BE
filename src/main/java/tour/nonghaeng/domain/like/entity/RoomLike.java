package tour.nonghaeng.domain.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.room.entity.Room;

@Entity
@Table(name = "ROOM_LIKES")
@DiscriminatorValue("room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomLike extends Like {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    private RoomLike(User user, Room room) {
        super(user);
        this.room = room;
    }
}
