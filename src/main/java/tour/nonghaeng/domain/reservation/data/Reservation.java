package tour.nonghaeng.domain.reservation.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.dto.ReservationCancelResponseDto;
import tour.nonghaeng.domain.reservation.dto.ReservationDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;
import tour.nonghaeng.global.infra.BaseTimeEntity;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.reservation.ReservationStateType;

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

    private boolean isWrittenReview;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Reservation(User user, Seller seller, ReservationStateType stateType, int numOfParticipant, int price, String reservationName, String number, String email, Payment payment) {
        this.user = user;
        this.seller = seller;
        this.stateType = stateType;
        this.numOfParticipant = numOfParticipant;
        this.price = price;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
        this.payment = payment;
        this.isWrittenReview = false;
    }

    public void setWrittenReview() {
        this.isWrittenReview = true;
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

    public void waitingReservation() {
        this.stateType = ReservationStateType.WAITING_RESERVATION;
    }

    public void cancelReservation() {
        this.stateType = ReservationStateType.CANCEL_RESERVATION;
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

    public abstract Long getEntityId();

    public abstract String getItemName();

}
