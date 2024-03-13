package tour.nonghaeng.domain.reservation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReservationResponseDto {
    private Long experienceReservationId;
    private String experienceName;
    private String reservationName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String number;
    private String email;
    private int numOfParticipant;
    private int finalPrice;

    @Builder
    public ExpReservationResponseDto(Long experienceReservationId, String experienceName, String reservationName, LocalDate reservationDate, LocalTime startTime, LocalTime endTime, String number, String email, int numOfParticipant, int finalPrice) {
        this.experienceReservationId = experienceReservationId;
        this.experienceName = experienceName;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.number = number;
        this.email = email;
        this.numOfParticipant = numOfParticipant;
        this.finalPrice = finalPrice;
    }

    public static ExpReservationResponseDto toDto(ExperienceReservation experienceReservation) {
        return ExpReservationResponseDto.builder()
                .experienceReservationId(experienceReservation.getId())
                .experienceName(experienceReservation.getExperience().getExperienceName())
                .reservationName(experienceReservation.getReservationName())
                .reservationDate(experienceReservation.getReservationDate())
                .startTime(experienceReservation.getExperienceRound().getStartTime())
                .endTime(experienceReservation.getExperienceRound().getEndTime())
                .number(experienceReservation.getNumber())
                .email(experienceReservation.getEmail())
                .numOfParticipant(experienceReservation.getNumOfParticipant())
                .finalPrice(experienceReservation.getPrice())
                .build();
    }
}
