package tour.nonghaeng.domain.reservation.dto.room;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.data.RoomReservation;
import tour.nonghaeng.domain.reservation.data.RoomReservationDate;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.room.data.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("room")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
@Slf4j
public class CreateRoomReservationDto extends CreateReservationDto<RoomReservation,Room> {

    private Long roomId;
    private int numOfRoom;
    private int numOfParticipant;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> reservationDates;
    private String reservationName;         //예약자명
    private String number;                  //연락처
    private String email;                   //이메일
    private int finalPrice;

    @Builder
    private CreateRoomReservationDto(Long roomId, int numOfRoom, int numOfParticipant, LocalDate startDate, LocalDate endDate, List<LocalDate> reservationDates, String reservationName, String number, String email, int finalPrice) {
        this.roomId = roomId;
        this.numOfRoom = numOfRoom;
        this.numOfParticipant = numOfParticipant;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationDates = reservationDates;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
        this.finalPrice = finalPrice;
    }

    @Override
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

    public void toSetLocalDateList() {
        // startDate와 endDate 사이의 날짜 리스트 생성
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = this.startDate;
        while (!currentDate.isAfter(this.endDate.minusDays(1))) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        this.reservationDates = dates;
    }

}
