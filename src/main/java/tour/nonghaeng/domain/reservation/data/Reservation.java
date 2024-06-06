package tour.nonghaeng.domain.reservation.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.etc.enums.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.dto.*;

@Entity
@Table(name="RESERVATIONS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public abstract class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Enumerated(EnumType.STRING)
    private ReservationStateType stateType;

    private int numOfParticipant;

    private int price;

    private String reservationName;

    private String number;

    private String email;

    public Reservation(User user, Seller seller, ReservationStateType stateType, int numOfParticipant, int price, String reservationName, String number, String email) {
        this.user = user;
        this.seller = seller;
        this.stateType = stateType;
        this.numOfParticipant = numOfParticipant;
        this.price = price;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
    }

    public boolean isWaitingState() {
        if (this.stateType == ReservationStateType.WAITING_RESERVATION) {
            return true;
        }
        return false;
    }

    public boolean isApproveState() {
        if (this.stateType == ReservationStateType.CONFIRM_RESERVATION) {
            return true;
        }
        return false;
    }

    public void approveReservation() {
        if (isWaitingState()) {
            this.stateType = ReservationStateType.CONFIRM_RESERVATION;
        }
    }

    public void notApproveReservation() {
        if (isWaitingState()) {
            this.stateType = ReservationStateType.NOT_CONFIRM_RESERVATION;
        }
    }

    public Reservation cancelReservation() {
        this.stateType = ReservationStateType.CANCEL_RESERVATION;
        return this;
    }

    public void changeCompleteReservation() {
        if (isApproveState()) {
            this.stateType = ReservationStateType.COMPLETE_RESERVATION;
        }
    }

    public abstract ReservationSummaryDto toSummaryDto();

    public abstract ReservationSummaryDto toSummaryDtoForSeller();

    public abstract ReservationDetailDto toDetailDto();

    public abstract ReservationDetailDto toDetailDtoForSeller(int remainParticipant);

    public abstract ReservationCancelResponseDto toCancelResponseDto(CancelPolicy cancelPolicy);

    public abstract ReservationResponseDto toReservationResponseDto();

}
