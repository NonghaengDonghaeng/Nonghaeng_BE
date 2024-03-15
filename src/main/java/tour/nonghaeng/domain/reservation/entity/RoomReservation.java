package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalDate;

@Entity
@Table(name = "ROOM_RESERVATIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private ReservationStateType stateType;

    private int price;

    private LocalDate reservationDate;

    private int numOfRoom;

    private int numOfParticipant;

    private String reservationName;

    private String number;

    private String email;

    @Builder
    private RoomReservation(User user, Seller seller, Room room, ReservationStateType stateType, int price, LocalDate reservationDate, int numOfRoom, int numOfParticipant, String reservationName, String number, String email) {
        this.user = user;
        this.seller = seller;
        this.room = room;
        this.stateType = stateType;
        this.price = price;
        this.reservationDate = reservationDate;
        this.numOfRoom = numOfRoom;
        this.numOfParticipant = numOfParticipant;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
    }

    public void approveReservation() {

        if (this.stateType.equals(ReservationStateType.WAITING_RESERVATION)) {
            this.stateType = ReservationStateType.CONFIRM_RESERVATION;
        }
    }

    public void notApproveReservation() {

        if (this.stateType.equals(ReservationStateType.WAITING_RESERVATION)) {
            this.stateType = ReservationStateType.NOT_CONFIRM_RESERVATION;
        }
    }

    public RoomReservation cancelReservation() {

        this.stateType = ReservationStateType.CANCEL_RESERVATION;
        return this;
    }
}
