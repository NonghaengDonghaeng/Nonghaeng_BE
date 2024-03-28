package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.room.entity.Room;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOM_RESERVATIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservation extends Reservation {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "roomReservation", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomReservationDate> reservationDates = new ArrayList<>();

    private int numOfRoom;




    @Builder
    private RoomReservation(User user, Room room, ReservationStateType stateType, int price, int numOfRoom, int numOfParticipant, String reservationName, String number, String email) {
        super(user,room.getSeller(),stateType,numOfParticipant,price,reservationName,number,email);
        this.room = room;
        this.numOfRoom = numOfRoom;
    }

    public void addRoomReservationDate(RoomReservationDate roomReservationDate) {
        this.reservationDates.add(roomReservationDate);
    }

}
