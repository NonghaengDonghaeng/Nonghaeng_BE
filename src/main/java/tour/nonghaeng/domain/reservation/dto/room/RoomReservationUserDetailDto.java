package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationUserDetailDto {

    private String reservationState;
    private String tourName;
    private String roomName;
    private Long roomId;
    private List<LocalDate> reservationDates;
    private String userName;
    private LocalDateTime reservationAt;
    private int numOfParticipant;
    private int numOfRoom;
    private int price;

    @Builder
    private RoomReservationUserDetailDto(String reservationState, String tourName, String roomName, Long roomId, List<LocalDate> reservationDates, String userName, LocalDateTime reservationAt, int numOfParticipant, int numOfRoom, int price) {
        this.reservationState = reservationState;
        this.tourName = tourName;
        this.roomName = roomName;
        this.roomId = roomId;
        this.reservationDates = reservationDates;
        this.userName = userName;
        this.reservationAt = reservationAt;
        this.numOfParticipant = numOfParticipant;
        this.numOfRoom = numOfRoom;
        this.price = price;
    }

    public static RoomReservationUserDetailDto toDto(RoomReservation roomReservation) {
        return RoomReservationUserDetailDto.builder()
                .reservationState(roomReservation.getStateType().getName())
                .tourName(roomReservation.getRoom().getTour().getName())
                .roomName(roomReservation.getRoom().getRoomName())
                .roomId(roomReservation.getRoom().getId())
                .reservationDates(roomReservation.getReservationDates().stream().map(roomReservationDate -> roomReservationDate.getReservationDate()).toList())
                .userName(roomReservation.getReservationName())
                .numOfParticipant(roomReservation.getNumOfParticipant())
                .numOfRoom(roomReservation.getNumOfRoom())
                .price(roomReservation.getPrice())
                .build();

    }
}
