package tour.nonghaeng.domain.reservation.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReservationUserDetailDto {

    private String reservationState;
    private String experienceName;
    private Long experienceId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String userName;
    private LocalDateTime reservationAt;
    private int numOfParticipant;
    private int price;

    @Builder
    public ExpReservationUserDetailDto(String reservationState, String experienceName, Long experienceId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime, String userName, LocalDateTime reservationAt, int numOfParticipant, int price) {
        this.reservationState = reservationState;
        this.experienceName = experienceName;
        this.experienceId = experienceId;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userName = userName;
        this.reservationAt = reservationAt;
        this.numOfParticipant = numOfParticipant;
        this.price = price;
    }

    public static ExpReservationUserDetailDto toDto(ExperienceReservation experienceReservation) {
        return ExpReservationUserDetailDto.builder()
                .reservationState(experienceReservation.getStateType().getName())
                .experienceName(experienceReservation.getExperience().getExperienceName())
                .experienceId(experienceReservation.getExperience().getId())
                .reservationDate(experienceReservation.getReservationDate())
                .startTime(experienceReservation.getExperienceRound().getStartTime())
                .endTime(experienceReservation.getExperienceRound().getEndTime())
                .userName(experienceReservation.getReservationName())
                .reservationAt(experienceReservation.getCreatedAt())
                .numOfParticipant(experienceReservation.getNumOfParticipant())
                .price(experienceReservation.getPrice())
                .build();
    }
}
