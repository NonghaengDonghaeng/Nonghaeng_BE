package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.payment.dto.RequestPaymentDto;
import tour.nonghaeng.domain.reservation.data.RoomReservation;
import tour.nonghaeng.domain.reservation.data.RoomReservationDate;
import tour.nonghaeng.domain.reservation.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservationResponseDto extends ReservationResponseDto {

    private Long roomReservationId;
    private String roomName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String reservationName;
    private String number;
    private String email;
    private int numOfRoom;
    private int numOfParticipant;
    private int finalPrice;

    @Builder
    private RoomReservationResponseDto(Long roomReservationId, String roomName, LocalDate startDate, LocalDate endDate, String reservationName, String number, String email, int numOfRoom, int numOfParticipant, int finalPrice, RequestPaymentDto paymentDto) {
        super(paymentDto);
        this.roomReservationId = roomReservationId;
        this.roomName = roomName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
        this.numOfRoom = numOfRoom;
        this.numOfParticipant = numOfParticipant;
        this.finalPrice = finalPrice;
    }

    public static RoomReservationResponseDto toDto(RoomReservation roomReservation) {
        RoomReservationResponseDto responseDto = RoomReservationResponseDto.builder()
                .roomReservationId(roomReservation.getId())
                .roomName(roomReservation.getRoom().getRoomName())
                .reservationName(roomReservation.getReservationName())
                .number(roomReservation.getNumber())
                .email(roomReservation.getEmail())
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
