package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationUserSummaryDto {

    private Long roomReservationId;
    private String roomName;
    private String reservationState;
    private List<LocalDate> reservationDates;
    private int price;
    private int numOfParticipant;
    private int numOfRoom;

    @Builder
    private RoomReservationUserSummaryDto(Long roomReservationId, String roomName, String reservationState, List<LocalDate> reservationDates, int price, int numOfParticipant, int numOfRoom) {
        this.roomReservationId = roomReservationId;
        this.roomName = roomName;
        this.reservationState = reservationState;
        this.reservationDates = reservationDates;
        this.price = price;
        this.numOfParticipant = numOfParticipant;
        this.numOfRoom = numOfRoom;
    }

    public static Page<RoomReservationUserSummaryDto> toPageDto(Page<RoomReservation> page) {
        return page.map(roomReservation -> RoomReservationUserSummaryDto.builder()
                .roomReservationId(roomReservation.getId())
                .roomName(roomReservation.getRoom().getRoomName())
                .reservationState(roomReservation.getStateType().getName())
                .reservationDates(roomReservation.getReservationDates()
                        .stream().map(roomReservationDate -> roomReservationDate.getReservationDate())
                        .toList())
                .price(roomReservation.getPrice())
                .numOfParticipant(roomReservation.getNumOfParticipant())
                .numOfRoom(roomReservation.getNumOfRoom())
                .build());
    }

}
