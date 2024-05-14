package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservationDate;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class CreateRoomReservationDto {

    private Long roomId;
    private int numOfRoom;
    private int numOfParticipant;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> reservationDates;
    private String reservationName;         //예약자명
    private String number;                  //연락처
    private String email;                   //이메일
    private int finalPrice;

    @Builder
    public CreateRoomReservationDto(Long roomId, int numOfRoom, int numOfParticipant, List<LocalDate> reservationDates, String reservationName, String number, String email, int finalPrice) {
        this.roomId = roomId;
        this.numOfRoom = numOfRoom;
        this.numOfParticipant = numOfParticipant;
        this.reservationDates = reservationDates;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
        this.finalPrice = finalPrice;
    }

    public RoomReservation toEntity(User user, Room room) {

        RoomReservation roomReservation = RoomReservation.builder()
                .user(user)
                .room(room)
                .stateType(ReservationStateType.WAITING_RESERVATION)
                .price(this.getFinalPrice())
                .numOfRoom(this.getNumOfRoom())
                .numOfParticipant(this.getNumOfParticipant())
                .reservationName(this.getReservationName())
                .number(this.getNumber())
                .email(this.getEmail())
                .build();

        this.reservationDates.forEach(reservationDate ->
                roomReservation.addRoomReservationDate(
                        RoomReservationDate.builder()
                                .roomReservation(roomReservation)
                                .reservationDate(reservationDate)
                                .build()
                ));
        return roomReservation;
    }
}
