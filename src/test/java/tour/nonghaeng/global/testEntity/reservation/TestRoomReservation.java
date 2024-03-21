package tour.nonghaeng.global.testEntity.reservation;

import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.entity.RoomReservationDate;
import tour.nonghaeng.domain.room.entity.Room;

import java.time.LocalDate;
import java.util.List;

public class TestRoomReservation {

    public static final ReservationStateType ROOM_RESERVATION_STATE_TYPE = ReservationStateType.WAITING_RESERVATION;
    public static final int ROOM_RESERVATION_PRICE = 10000;
    public static final int ROOM_RESERVATION_NUM_OF_ROOM = 1;
    public static final int ROOM_RESERVATION_NUM_OF_PARTICIPANT = 2;
    private static final String ROOM_RESERVATION_NAME = "king";
    private static final String ROOM_RESERVATION_NUMBER = "010-9876-9876";
    private static final String ROOM_RESERVATION_EMAIL = "king@email.com";



    public static RoomReservation makeTestRoomReservation(User user, Room room, List<LocalDate> reservationDates){
        RoomReservation testRoomReservation = RoomReservation.builder()
                .user(user)
                .room(room)
                .stateType(ROOM_RESERVATION_STATE_TYPE)
                .price(ROOM_RESERVATION_PRICE)
                .numOfRoom(ROOM_RESERVATION_NUM_OF_ROOM)
                .numOfParticipant(ROOM_RESERVATION_NUM_OF_PARTICIPANT)
                .reservationName(ROOM_RESERVATION_NAME)
                .number(ROOM_RESERVATION_NUMBER)
                .email(ROOM_RESERVATION_EMAIL)
                .build();
        reservationDates.forEach(reservationDate ->
                testRoomReservation.addRoomReservationDate(
                        RoomReservationDate.builder()
                                .roomReservation(testRoomReservation)
                                .reservationDate(reservationDate)
                                .build()
                ));
        return testRoomReservation;
    }
    public static RoomReservation makeTestRoomReservation(User user, Room room, List<LocalDate> reservationDates,int numOfRoom){
        RoomReservation testRoomReservation = RoomReservation.builder()
                .user(user)
                .room(room)
                .stateType(ROOM_RESERVATION_STATE_TYPE)
                .price(ROOM_RESERVATION_PRICE)
                .numOfRoom(numOfRoom)
                .numOfParticipant(ROOM_RESERVATION_NUM_OF_PARTICIPANT)
                .reservationName(ROOM_RESERVATION_NAME)
                .number(ROOM_RESERVATION_NUMBER)
                .email(ROOM_RESERVATION_EMAIL)
                .build();
        reservationDates.forEach(reservationDate ->
                testRoomReservation.addRoomReservationDate(
                        RoomReservationDate.builder()
                                .roomReservation(testRoomReservation)
                                .reservationDate(reservationDate)
                                .build()
                ));
        return testRoomReservation;
    }
}
