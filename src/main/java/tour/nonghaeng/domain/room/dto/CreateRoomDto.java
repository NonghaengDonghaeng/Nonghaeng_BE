package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import tour.nonghaeng.domain.etc.room.RoomType;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateRoomDto(
    RoomType roomType,
    String roomName,
    String summary,
    int pricePeak,
    int priceOffPeak,
    int priceHoliday,
    int standardCapacity,
    int maxCapacity,
    int additionalCost,
    LocalTime checkinTime,
    LocalTime checkoutTime,
    int numOfRoom,
    String roomConfiguration,
    String inclusions,
    String requirement,
    String facilities,
    String usageTips,
    String precautions
) {

    public Room toEntity(Tour tour) {
        return Room.builder()
                .tour(tour)
                .roomType(this.roomType)
                .roomName(this.roomName)
                .summary(this.summary)
                .pricePeak(this.pricePeak)
                .priceOffPeak(this.priceOffPeak)
                .priceHoliday(this.priceHoliday)
                .standardCapacity(this.standardCapacity)
                .maxCapacity(this.maxCapacity)
                .additionalCost(this.additionalCost)
                .checkinTime(this.checkinTime)
                .checkoutTime(this.checkoutTime)
                .numOfRoom(this.numOfRoom)
                .roomConfiguration(this.roomConfiguration)
                .inclusions(this.inclusions)
                .requirement(this.requirement)
                .facilities(this.facilities)
                .usageTips(this.usageTips)
                .precautions(this.precautions)
                .build();

    }
}
