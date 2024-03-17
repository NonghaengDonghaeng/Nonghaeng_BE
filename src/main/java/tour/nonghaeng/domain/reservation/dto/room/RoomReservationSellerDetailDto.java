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
public class RoomReservationSellerDetailDto {

    private String reservationState;
    private String roomName;
    private Long roomId;
    private List<LocalDate> reservationDates;
    private LocalDateTime reservationAt;
    private String userName;
    private String number;
    private String email;
    private int numOfParticipant;
    private int numOfRoom;
    private int price;

    @Builder
    private RoomReservationSellerDetailDto(String reservationState, String roomName, Long roomId, List<LocalDate> reservationDates, LocalDateTime reservationAt, String userName, String number, String email, int numOfParticipant, int numOfRoom, int price) {
        this.reservationState = reservationState;
        this.roomName = roomName;
        this.roomId = roomId;
        this.reservationDates = reservationDates;
        this.reservationAt = reservationAt;
        this.userName = userName;
        this.number = number;
        this.email = email;
        this.numOfParticipant = numOfParticipant;
        this.numOfRoom = numOfRoom;
        this.price = price;
    }

    public static RoomReservationSellerDetailDto toDto(RoomReservation roomReservation) {

        return RoomReservationSellerDetailDto.builder()
                .reservationState(roomReservation.getStateType().getName())
                .roomName(roomReservation.getRoom().getRoomName())
                .roomId(roomReservation.getRoom().getId())
                .reservationAt(roomReservation.getCreatedAt())
                .userName(roomReservation.getReservationName())
                .number(roomReservation.getNumber())
                .email(roomReservation.getEmail())
                .numOfParticipant(roomReservation.getNumOfParticipant())
                .numOfRoom(roomReservation.getNumOfRoom())
                .price(roomReservation.getPrice())
                .build();
    }
}
