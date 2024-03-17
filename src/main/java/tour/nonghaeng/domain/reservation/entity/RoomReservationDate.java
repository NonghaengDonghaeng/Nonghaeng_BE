package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ROOM_RESERVATION_DATES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_reservation_id")
    private RoomReservation roomReservation;

    private LocalDate reservationDate;

    @Builder
    private RoomReservationDate(RoomReservation roomReservation,LocalDate reservationDate) {
        this.roomReservation = roomReservation;
        this.reservationDate = reservationDate;
    }
}
