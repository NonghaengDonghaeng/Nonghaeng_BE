package tour.nonghaeng.domain.reservation.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.reservation.dto.payment.RequestPaymentDto;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.dto.ReservationCancelResponseDto;
import tour.nonghaeng.domain.reservation.dto.ReservationDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;
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
    private RoomReservation(User user, Room room, ReservationStateType stateType, int price, int numOfRoom, int numOfParticipant, String reservationName, String number, String email, Payment payment) {
        super(user, room.getSeller(), stateType, numOfParticipant, price, reservationName, number, email, payment);
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
                        .stream().map(RoomReservationDate::getReservationDate)
                        .toList())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
                .numOfRoom(this.getNumOfRoom())
                .isWrittenReview(this.isWrittenReview())
                .type("room")
                .build();
    }

    @Override
    public ReservationSummaryDto toSummaryDtoForSeller() {

        return RoomReservationSummaryDtoForSeller.builder()
                .roomReservationId(this.getId())
                .reservationState(this.getStateType().getName())
                .roomName(this.getRoom().getRoomName())
                .reservationDates(this.getReservationDates()
                        .stream().map(RoomReservationDate::getReservationDate)
                        .toList())
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
                .reservationDates(this.getReservationDates()
                        .stream().map(RoomReservationDate::getReservationDate)
                        .toList())
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
                .reservationDates(this.reservationDates
                        .stream().map(RoomReservationDate::getReservationDate)
                        .toList())
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
                .paymentDto(RequestPaymentDto.toDto(this))
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

    @Override
    public Long getEntityId() {
        return this.room.getId();
    }

    @Override
    public String getItemName() {
        return this.room.getRoomName();
    }


}
