package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.ReservationUserDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationUserSummaryDto;

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

    public abstract ReservationUserSummaryDto toUserSummaryDto();

    public abstract ReservationUserDetailDto toUserDetailDto();

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", seller=" + seller +
                ", stateType=" + stateType +
                ", numOfParticipant=" + numOfParticipant +
                ", price=" + price +
                ", reservationName='" + reservationName + '\'' +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
