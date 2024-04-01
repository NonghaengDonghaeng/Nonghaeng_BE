package tour.nonghaeng.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.exp.*;

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

    @Override
    public ReservationUserSummaryDto toUserSummaryDto() {
        return ExpReservationUserSummaryDto.builder()
                .experienceReservationId(this.getId())
                .experienceName(this.getExperience().getExperienceName())
                .reservationState(this.getStateType().getName())
                .reservationDate(this.getReservationDate())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
                .build();
    }

    @Override
    public ReservationUserDetailDto toUserDetailDto() {
        return ExpReservationUserDetailDto.builder()
                .reservationState(this.getStateType().getName())
                .experienceName(this.getExperience().getExperienceName())
                .experienceId(this.getExperience().getId())
                .reservationDate(this.getReservationDate())
                .startTime(this.getExperienceRound().getStartTime())
                .endTime(this.getExperienceRound().getEndTime())
                .userName(this.getReservationName())
                .reservationAt(this.getCreatedAt())
                .numOfParticipant(this.getNumOfParticipant())
                .reservationAt(super.getCreatedAt())
                .price(this.getPrice())
                .build();
    }

    @Override
    public ReservationSellerSummaryDto toSellerSummaryDto() {
        return ExpReservationSellerSummaryDto.builder()
                .reservationState(this.getStateType().getName())
                .experienceName(this.getExperience().getExperienceName())
                .experienceReservationId(this.getId())
                .reservationDate(this.getReservationDate())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
                .build();
    }

    @Override
    public ReservationSellerDetailDto toSellerDetailDto(int remainParticipant) {
        return ExpReservationSellerDetailDto.builder()
                .reservationState(this.getStateType().getName())
                .experienceName(this.getExperience().getExperienceName())
                .experienceId(this.getExperience().getId())
                .reservationDate(this.getReservationDate())
                .startTime(this.getExperienceRound().getStartTime())
                .endTime(this.getExperienceRound().getEndTime())
                .userName(this.getReservationName())
                .reservationAt(this.getCreatedAt())
                .numOfParticipant(this.getNumOfParticipant())
                .remainParticipant(remainParticipant)
                .price(this.getPrice())
                .build();
    }

    @Override
    public ReservationCancelResponseDto toCancelResponseDto(CancelPolicy cancelPolicy) {
        return ExpReservationCancelResponseDto.builder()
                .cancelPolicy(cancelPolicy.getName())
                .percent(cancelPolicy.getPercent())
                .originPayPoint(this.getPrice())
                .payBackPoint((int) (this.getPrice() * (1 - cancelPolicy.getPercent())))
                .remainPoint(this.getUser().getPoint())
                .experienceName(this.getExperience().getExperienceName())
                .experienceReservationId(this.getId())
                .build();
    }

}
