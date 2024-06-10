package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.enums.room.RoomType;
import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.infra.dto.CreateDto;

import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class CreateRoomDto extends CreateDto {
    private RoomType roomType;
    private String roomName;
    private String summary;
    private int pricePeak;
    private int priceOffPeak;
    private int priceHoliday;
    private int standardCapacity;
    private int maxCapacity;
    private int additionalCost;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private int numOfRoom;
    private String roomConfiguration;
    private String inclusions;
    private String requirement;
    private String facilities;
    private String usageTips;
    private String precautions;


    public CreateRoomDto(RoomType roomType, String roomName, String summary, int pricePeak, int priceOffPeak, int priceHoliday, int standardCapacity, int maxCapacity, int additionalCost, LocalTime checkinTime, LocalTime checkoutTime, int numOfRoom, String roomConfiguration, String requirement, String inclusions, String facilities, String usageTips, String precautions) {

        this.roomType = roomType;
        this.roomName = roomName;
        this.summary = summary;
        this.pricePeak = pricePeak;
        this.priceOffPeak = priceOffPeak;
        this.standardCapacity = standardCapacity;
        this.priceHoliday = priceHoliday;
        this.maxCapacity = maxCapacity;
        this.additionalCost = additionalCost;
        this.checkinTime = checkinTime;
        this.numOfRoom = numOfRoom;
        this.checkoutTime = checkoutTime;
        this.roomConfiguration = roomConfiguration;
        this.requirement = requirement;
        this.inclusions = inclusions;
        this.facilities = facilities;
        this.usageTips = usageTips;
        this.precautions = precautions;
    }

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
