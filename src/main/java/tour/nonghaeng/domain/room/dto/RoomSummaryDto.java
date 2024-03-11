package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomSummaryDto {

    private Long roomId;
    private String roomName;
    private int numOfRoom;
    private int price;
    private int priceHoliday;
    private int standardCapacity;
    private int maxCapacity;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private String roomConfiguration;

    @Builder
    public RoomSummaryDto(Long roomId, String roomName, int numOfRoom, int price, int priceHoliday, int standardCapacity, int maxCapacity, LocalTime checkinTime, LocalTime checkoutTime, String roomConfiguration) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.numOfRoom = numOfRoom;
        this.price = price;
        this.priceHoliday = priceHoliday;
        this.standardCapacity = standardCapacity;
        this.maxCapacity = maxCapacity;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.roomConfiguration = roomConfiguration;
    }

    public static RoomSummaryDto toDto(Room room) {
        return RoomSummaryDto.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .numOfRoom(room.getNumOfRoom()) //TODO: 날짜에 따라 예약 수 보고 빼주기
                .price(room.getPriceOffPeak())  //TODO: 성수기,비성수기에 따라 자동으로 그 값이 나오도록
                .priceHoliday(room.getPriceHoliday())
                .standardCapacity(room.getStandardCapacity())
                .maxCapacity(room.getMaxCapacity())
                .checkinTime(room.getCheckinTime())
                .checkoutTime(room.getCheckoutTime())
                .roomConfiguration(room.getRoomConfiguration())
                .build();
    }
}
