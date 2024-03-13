package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;

import java.time.LocalDate;

@Entity
@Table(name = "EXPERIENCE_RESERVATIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @ManyToOne
    @JoinColumn(name = "experience_round_id")
    private ExperienceRound experienceRound;

    @Enumerated(EnumType.STRING)                //
    private ReservationStateType stateType;

    private int price;

    private LocalDate reservationDate;

    private int numOfParticipant;

    private String reservationName;

    private String number;

    private String email;

    @Builder
    public ExperienceReservation(User user, Seller seller, Experience experience, ExperienceRound experienceRound, ReservationStateType stateType, int price, LocalDate reservationDate, int numOfParticipant, String reservationName, String number, String email) {
        this.user = user;
        this.seller = seller;
        this.experience = experience;
        this.experienceRound = experienceRound;
        this.stateType = stateType;
        this.price = price;
        this.reservationDate = reservationDate;
        this.numOfParticipant = numOfParticipant;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
    }

    public void approveReservation() {
        if (this.stateType.equals(ReservationStateType.WAITING_RESERVATION)) {
            this.stateType = ReservationStateType.CONFIRM_RESERVATION;
        }
    }



}
