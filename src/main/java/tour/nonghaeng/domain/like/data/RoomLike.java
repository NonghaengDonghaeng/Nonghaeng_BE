package tour.nonghaeng.domain.like.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.room.data.Room;

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
