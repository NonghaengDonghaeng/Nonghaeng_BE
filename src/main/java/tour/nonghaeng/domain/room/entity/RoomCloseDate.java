package tour.nonghaeng.domain.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ROOM_CLOSE_DATES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomCloseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_close_date_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;

    LocalDate closeDate;

    @Builder
    public RoomCloseDate(Room room, LocalDate closeDate) {
        this.room = room;
        this.closeDate = closeDate;
    }
}
