package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.reservation.dto.payment.RequestPaymentDto;
import tour.nonghaeng.domain.reservation.data.RoomReservation;
import tour.nonghaeng.domain.reservation.data.RoomReservationDate;
import tour.nonghaeng.domain.reservation.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationResponseDto extends ReservationResponseDto {

    private final Long roomReservationId;
    private final String roomName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private final int numOfRoom;
    private final int numOfParticipant;
    private final int finalPrice;

    @Builder
    private RoomReservationResponseDto(Long roomReservationId, String roomName, LocalDate startDate, LocalDate endDate, int numOfRoom, int numOfParticipant, int finalPrice, RequestPaymentDto paymentDto) {
        super(paymentDto);
        this.roomReservationId = roomReservationId;
        this.roomName = roomName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numOfRoom = numOfRoom;
        this.numOfParticipant = numOfParticipant;
        this.finalPrice = finalPrice;
    }

    public static RoomReservationResponseDto toDto(RoomReservation roomReservation) {
        RoomReservationResponseDto responseDto = RoomReservationResponseDto.builder()
                .roomReservationId(roomReservation.getId())
                .roomName(roomReservation.getRoom().getRoomName())
                .numOfRoom(roomReservation.getNumOfRoom())
                .numOfParticipant(roomReservation.getNumOfParticipant())
                .finalPrice(roomReservation.getPrice())
                .build();

        responseDto.setStartDateAndEndDate(roomReservation);

        return responseDto;
    }


    public void setStartDateAndEndDate (RoomReservation roomReservation) {

        List<LocalDate> reservationDates = roomReservation.getReservationDates()
                .stream().map(RoomReservationDate::getReservationDate).toList();
        this.startDate = reservationDates.stream().min(LocalDate::compareTo).orElse(null);
        LocalDate endDate = reservationDates.stream().max(LocalDate::compareTo).orElse(null);
        assert endDate != null;
        this.endDate = endDate.plusDays(1);
    }
}
