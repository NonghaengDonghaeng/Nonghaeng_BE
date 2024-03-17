package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationResponseDto {

    private Long roomReservationId;
    private String roomName;
    private List<LocalDate> reservationDates;
    private String reservationName;
    private String number;
    private String email;
    private int numOfRoom;
    private int numOfParticipant;
    private int finalPrice;

    @Builder
    private RoomReservationResponseDto(Long roomReservationId, String roomName, List<LocalDate> reservationDates, String reservationName, String number, String email, int numOfRoom, int numOfParticipant, int finalPrice) {
        this.roomReservationId = roomReservationId;
        this.roomName = roomName;
        this.reservationDates = reservationDates;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
        this.numOfRoom = numOfRoom;
        this.numOfParticipant = numOfParticipant;
        this.finalPrice = finalPrice;
    }

    public static RoomReservationResponseDto toDto(RoomReservation roomReservation) {
        return RoomReservationResponseDto.builder()
                .roomReservationId(roomReservation.getId())
                .roomName(roomReservation.getRoom().getRoomName())
                .reservationDates(roomReservation.getReservationDates().stream().map(roomReservationDate ->
                   roomReservationDate.getReservationDate()
                ).toList())
                .reservationName(roomReservation.getReservationName())
                .number(roomReservation.getNumber())
                .email(roomReservation.getEmail())
                .numOfRoom(roomReservation.getNumOfRoom())
                .numOfParticipant(roomReservation.getNumOfParticipant())
                .finalPrice(roomReservation.getPrice())
                .build();
    }
}
