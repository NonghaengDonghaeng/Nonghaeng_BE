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
public class RoomDetailDto {
    private String roomName;
    private String roomTypeName;
    private String summary;
    private int pricePeak;
    private int priceOffPeak;
    private int priceHoliday;
    private int standardCapacity;
    private int maxCapacity;
    private int additionalCost;
    private LocalTime checkinTime;
    private LocalTime checkoutTime;
    private int currentNumOfRoom;
    private String roomConfiguration;
    private String inclusions;
    private String requirement;
    private String facilities;
    private String usageTips;
    private String precautions;
    private Long tourId;
    private String tourName;

    @Builder
    public RoomDetailDto(String roomName, String roomTypeName, String summary, int pricePeak, int priceOffPeak, int priceHoliday, int standardCapacity, int maxCapacity, int additionalCost, LocalTime checkinTime, LocalTime checkoutTime, int currentNumOfRoom, String roomConfiguration, String inclusions, String requirement, String facilities, String usageTips, String precautions, Long tourId, String tourName) {
        this.roomName = roomName;
        this.roomTypeName = roomTypeName;
        this.summary = summary;
        this.pricePeak = pricePeak;
        this.priceOffPeak = priceOffPeak;
        this.priceHoliday = priceHoliday;
        this.standardCapacity = standardCapacity;
        this.maxCapacity = maxCapacity;
        this.additionalCost = additionalCost;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.currentNumOfRoom = currentNumOfRoom;
        this.roomConfiguration = roomConfiguration;
        this.inclusions = inclusions;
        this.requirement = requirement;
        this.facilities = facilities;
        this.usageTips = usageTips;
        this.precautions = precautions;
        this.tourId = tourId;
        this.tourName = tourName;
    }

    public static RoomDetailDto toDto(Room room) {
        return RoomDetailDto.builder()
                .roomName(room.getRoomName())
                .roomTypeName(room.getRoomType().getName())
                .summary(room.getSummary())
                .pricePeak(room.getPricePeak())
                .priceOffPeak(room.getPriceOffPeak())
                .priceHoliday(room.getPriceHoliday())
                .standardCapacity(room.getStandardCapacity())
                .maxCapacity(room.getMaxCapacity())
                .additionalCost(room.getAdditionalCost())
                .checkinTime(room.getCheckinTime())
                .checkoutTime(room.getCheckoutTime())
                .currentNumOfRoom(room.getNumOfRoom())
                .roomConfiguration(room.getRoomConfiguration())
                .inclusions(room.getInclusions())
                .requirement(room.getRequirement())
                .facilities(room.getFacilities())
                .usageTips(room.getUsageTips())
                .precautions(room.getPrecautions())
                .tourId(room.getTour().getId())
                .tourName(room.getTour().getName())
                .build();
    }

    public void setCurrentNumOfRoom(int reservedNumOfRoom) {
        this.currentNumOfRoom -= reservedNumOfRoom;
    }
}
