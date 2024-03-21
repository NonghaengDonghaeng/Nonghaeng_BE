package tour.nonghaeng.global.room;

import tour.nonghaeng.domain.etc.room.RoomType;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalTime;

public class TestRoom {

    public static final String ROOM_NAME = "testRoom";
    public static final String ROOM_SUMMARY = "온돌이 있는 뜨근한 방";
    public static final int ROOM_PRICE_PEAK = 20000;
    public static final int ROOM_PRICE_OFF_PEAK = 10000;
    public static final int ROOM_PRICE_HOLIDAY = 15000;
    public static final int ROOM_STANDARD_CAPACITY = 2;
    public static final int ROOM_MAX_CAPACITY = 4;
    public static final int ROOM_ADDITIONAL_COST = 5000;
    public static final LocalTime ROOM_CHECKIN_TIME = LocalTime.of(3, 00);
    public static final LocalTime ROOM_CHECKOUT_TIME = LocalTime.of(11, 00);
    public static final int ROOM_NUM_OF_ROOM = 5;

    public static int num = -1;


    public static Room makeTestRoom(Tour tour) {
        num++;
        return Room.builder()
                .tour(tour)
                .roomType(RoomType.VILLAGE)
                .roomName(ROOM_NAME + String.valueOf(num))
                .summary(ROOM_SUMMARY + String.valueOf(num))
                .pricePeak(ROOM_PRICE_PEAK)
                .priceOffPeak(ROOM_PRICE_OFF_PEAK)
                .priceHoliday(ROOM_PRICE_HOLIDAY)
                .standardCapacity(ROOM_STANDARD_CAPACITY)
                .maxCapacity(ROOM_MAX_CAPACITY)
                .additionalCost(ROOM_ADDITIONAL_COST)
                .checkinTime(ROOM_CHECKIN_TIME)
                .checkoutTime(ROOM_CHECKOUT_TIME)
                .numOfRoom(ROOM_NUM_OF_ROOM)
                .build();
    }

    public static Room makeTestRoom(Tour tour,int morePrice) {
        return Room.builder()
                .tour(tour)
                .roomType(RoomType.VILLAGE)
                .roomName(ROOM_NAME + String.valueOf(num++))
                .summary(ROOM_SUMMARY + String.valueOf(num++))
                .pricePeak(ROOM_PRICE_PEAK+morePrice)
                .priceOffPeak(ROOM_PRICE_OFF_PEAK+morePrice)
                .priceHoliday(ROOM_PRICE_HOLIDAY+morePrice)
                .standardCapacity(ROOM_STANDARD_CAPACITY)
                .maxCapacity(ROOM_MAX_CAPACITY)
                .additionalCost(ROOM_ADDITIONAL_COST)
                .checkinTime(ROOM_CHECKIN_TIME)
                .checkoutTime(ROOM_CHECKOUT_TIME)
                .numOfRoom(ROOM_NUM_OF_ROOM)
                .build();
    }
}
