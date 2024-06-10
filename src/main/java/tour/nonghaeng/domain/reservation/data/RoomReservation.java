package tour.nonghaeng.domain.reservation.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.enums.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.room.*;
import tour.nonghaeng.domain.room.data.Room;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOM_RESERVATIONS")
@DiscriminatorValue("room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReservation extends Reservation {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "roomReservation", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomReservationDate> reservationDates = new ArrayList<>();

    private int numOfRoom;


    @Builder
    private RoomReservation(User user, Room room, ReservationStateType stateType, int price, int numOfRoom, int numOfParticipant, String reservationName, String number, String email) {
        super(user,room.getSeller(),stateType,numOfParticipant,price,reservationName,number,email);
        this.room = room;
        this.numOfRoom = numOfRoom;
    }

    public void addRoomReservationDate(RoomReservationDate roomReservationDate) {
        this.reservationDates.add(roomReservationDate);
    }

    @Override
    public ReservationSummaryDto toSummaryDto() {
        return RoomReservationSummaryDto.builder()
                .roomReservationId(this.getId())
                .roomName(this.getRoom().getRoomName())
                .reservationState(this.getStateType().getName())
                .reservationDates(this.getReservationDates()
                        .stream().map(roomReservationDate -> roomReservationDate.getReservationDate())
                        .toList())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
                .numOfRoom(this.getNumOfRoom())
                .type("room")
                .build();
    }

    @Override
    public ReservationSummaryDto toSummaryDtoForSeller() {
        return RoomReservationSummaryDtoForSeller.builder()
                .roomReservationId(this.getId())
                .reservationState(this.getStateType().getName())
                .roomName(this.getRoom().getRoomName())
                .reservationDates(this.getReservationDates().stream().map(RoomReservationDate::getReservationDate).toList())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
                .numOfRoom(this.getNumOfRoom())
                .build();
    }

    @Override
    public ReservationDetailDto toDetailDto() {

        return RoomReservationDetailDto.builder()
                .reservationState(this.getStateType().getName())
                .tourName(this.getRoom().getTour().getName())
                .roomName(this.getRoom().getRoomName())
                .roomId(this.getRoom().getId())
                .reservationDates(this.getReservationDates().stream().map(roomReservationDate -> roomReservationDate.getReservationDate()).toList())
                .userName(this.getReservationName())
                .numOfParticipant(this.getNumOfParticipant())
                .numOfRoom(this.getNumOfRoom())
                .price(this.getPrice())
                .reservationAt(super.getCreatedAt())
                .build();
    }

    @Override
    public ReservationDetailDto toDetailDtoForSeller(int remainParticipant) {
        return RoomReservationDetailDtoForSeller.builder()
                .reservationState(this.getStateType().getName())
                .roomName(this.getRoom().getRoomName())
                .roomId(this.getRoom().getId())
                .reservationAt(this.getCreatedAt())
                .userName(this.getReservationName())
                .number(this.getNumber())
                .email(this.getEmail())
                .numOfParticipant(this.getNumOfParticipant())
                .numOfRoom(this.getNumOfRoom())
                .price(this.getPrice())
                .build();
    }



    @Override
    public ReservationCancelResponseDto toCancelResponseDto(CancelPolicy cancelPolicy) {
        return RoomReservationCancelResponseDto.builder()
                .cancelPolicy(cancelPolicy.getName())
                .percent(cancelPolicy.getPercent())
                .originPayPoint(this.getPrice())
                .payBackPoint((int) (this.getPrice() * (1 - cancelPolicy.getPercent())))
                .remainPoint(this.getUser().getPoint())
                .roomName(this.getRoom().getRoomName())
                .roomReservationId(this.getId())
                .build();
    }

    @Override
    public ReservationResponseDto toReservationResponseDto() {
        RoomReservationResponseDto responseDto = RoomReservationResponseDto.builder()
                .roomReservationId(this.getId())
                .roomName(this.getRoom().getRoomName())
                .reservationName(this.getReservationName())
                .number(this.getNumber())
                .email(this.getEmail())
                .numOfRoom(this.getNumOfRoom())
                .numOfParticipant(this.getNumOfParticipant())
                .finalPrice(this.getPrice())
                .build();

        responseDto.setStartDateAndEndDate(this);

        return responseDto;
    }


}
