package tour.nonghaeng.domain.reservation.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.payment.data.Payment;
import tour.nonghaeng.domain.payment.dto.RequestPaymentDto;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.exp.*;

import java.time.LocalDate;

@Entity
@Table(name = "EXPERIENCE_RESERVATIONS")
@DiscriminatorValue("experience")
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
    public ExperienceReservation(User user, ExperienceRound experienceRound, ReservationStateType stateType, int price, LocalDate reservationDate, int numOfParticipant, String reservationName, String number, String email, String reservationUid, Payment payment) {
        super(user, experienceRound.getExperience().getSeller(), stateType, numOfParticipant, price, reservationName, number, email,reservationUid,payment);
        this.experience = experienceRound.getExperience();
        this.experienceRound = experienceRound;
        this.reservationDate = reservationDate;
    }

    @Override
    public ReservationSummaryDto toSummaryDto() {
        return ExpReservationSummaryDto.builder()
                .experienceReservationId(this.getId())
                .experienceName(this.getExperience().getExperienceName())
                .reservationState(this.getStateType().getName())
                .reservationDate(this.getReservationDate())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
                .type("experience")
                .build();
    }

    @Override
    public ReservationDetailDto toDetailDto() {
        return ExpReservationDetailDto.builder()
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
    public ReservationDetailDto toDetailDtoForSeller(int remainParticipant) {
        return ExpReservationDetailDtoForSeller.builder()
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
    public ReservationSummaryDto toSummaryDtoForSeller() {
        return ExpReservationSummaryDtoForSeller.builder()
                .reservationState(this.getStateType().getName())
                .experienceName(this.getExperience().getExperienceName())
                .experienceReservationId(this.getId())
                .reservationDate(this.getReservationDate())
                .price(this.getPrice())
                .numOfParticipant(this.getNumOfParticipant())
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

    @Override
    public ReservationResponseDto toReservationResponseDto(RequestPaymentDto paymentDto) {
        return ExpReservationResponseDto.builder()
                .paymentDto(paymentDto)
                .experienceReservationId(this.getId())
                .experienceName(this.getExperience().getExperienceName())
                .reservationName(this.getReservationName())
                .reservationDate(this.getReservationDate())
                .startTime(this.getExperienceRound().getStartTime())
                .endTime(this.getExperienceRound().getEndTime())
                .number(this.getNumber())
                .email(this.getEmail())
                .numOfParticipant(this.getNumOfParticipant())
                .finalPrice(this.getPrice())
                .build();
    }

    @Override
    public Long getEntityId() {
        return this.experience.getId();
    }

    @Override
    public String getItemName() {
        return this.experience.getExperienceName();
    }


}
