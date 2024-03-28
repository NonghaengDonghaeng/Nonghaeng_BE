package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.controller.Reservation;

import java.time.LocalDate;

@Entity
@Table(name = "EXPERIENCE_RESERVATIONS")
@DiscriminatorValue("experience_reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceReservation extends Reservation {

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @ManyToOne
    @JoinColumn(name = "experience_round_id")
    private ExperienceRound experienceRound;

    private LocalDate reservationDate;


    @Builder
    public ExperienceReservation(User user, ExperienceRound experienceRound, ReservationStateType stateType, int price, LocalDate reservationDate, int numOfParticipant, String reservationName, String number, String email) {
        super(user, experienceRound.getExperience().getSeller(), stateType, numOfParticipant, price, reservationName, number, email);
        this.experience = experienceRound.getExperience();
        this.experienceRound = experienceRound;
        this.reservationDate = reservationDate;
    }
}
