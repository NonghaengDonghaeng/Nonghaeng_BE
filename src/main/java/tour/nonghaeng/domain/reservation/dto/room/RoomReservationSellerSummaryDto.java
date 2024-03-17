package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservationDate;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationSellerSummaryDto {

    private Long roomReservationId;
    private String reservationState;
    private String roomName;
    private List<LocalDate> reservationDates;
    private int price;
    private int numOfParticipant;
    private int numOfRoom;

    @Builder
    private RoomReservationSellerSummaryDto(Long roomReservationId, String reservationState, String roomName, List<LocalDate> reservationDates, int price, int numOfParticipant, int numOfRoom) {
        this.roomReservationId = roomReservationId;
        this.reservationState = reservationState;
        this.roomName = roomName;
        this.reservationDates = reservationDates;
        this.price = price;
        this.numOfParticipant = numOfParticipant;
        this.numOfRoom = numOfRoom;
    }

    public static Page<RoomReservationSellerSummaryDto> toPageDto(Page<RoomReservation> page) {
        return page.map(roomReservation -> RoomReservationSellerSummaryDto.builder()
                .roomReservationId(roomReservation.getId())
                .reservationState(roomReservation.getStateType().getName())
                .roomName(roomReservation.getRoom().getRoomName())
                .reservationDates(roomReservation.getReservationDates().stream().map(RoomReservationDate::getReservationDate).toList())
                .price(roomReservation.getPrice())
                .numOfParticipant(roomReservation.getNumOfParticipant())
                .numOfRoom(roomReservation.getNumOfRoom())
                .build());
    }
}
