package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomSummaryDto {

    private Long roomId;
    private String roomName;
    private int currentNumOfRoom;
    private int price;
    private int priceHoliday;
    private int standardCapacity;
    private int maxCapacity;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private String roomConfiguration;
    private PhotoInfoDto photoInfoDto;
    private int likes;

    @Builder
    public RoomSummaryDto(Long roomId, String roomName, int currentNumOfRoom, int price, int priceHoliday, int standardCapacity, int maxCapacity, LocalTime checkinTime, LocalTime checkoutTime, String roomConfiguration, PhotoInfoDto photoInfoDto,int likes) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.currentNumOfRoom = currentNumOfRoom;
        this.price = price;
        this.priceHoliday = priceHoliday;
        this.standardCapacity = standardCapacity;
        this.maxCapacity = maxCapacity;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.roomConfiguration = roomConfiguration;
        this.photoInfoDto = photoInfoDto;
        this.likes = likes;
    }

    public static RoomSummaryDto toDto(Room room,int reservationOfRoom) {
        return RoomSummaryDto.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .currentNumOfRoom(room.getNumOfRoom()-reservationOfRoom)
                .price(room.getPriceOffPeak())  //TODO: 성수기,비성수기에 따라 자동으로 그 값이 나오도록
                .priceHoliday(room.getPriceHoliday())
                .standardCapacity(room.getStandardCapacity())
                .maxCapacity(room.getMaxCapacity())
                .checkinTime(room.getCheckinTime())
                .checkoutTime(room.getCheckoutTime())
                .roomConfiguration(room.getRoomConfiguration())
                .photoInfoDto(room.findRepresentPhoto().isPresent()?
                        PhotoInfoDto.toDto(room.findRepresentPhoto().get()):null)
                .likes(room.getRoomLikes().size())
                .build();
    }



    public void setCurrentNumOfRoom(int reservedNumOfRoom) {
        this.currentNumOfRoom -= reservedNumOfRoom;
    }
}
