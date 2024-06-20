package tour.nonghaeng.domain.reservation.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.reservation.dto.payment.RequestPaymentDto;
import tour.nonghaeng.domain.reservation.data.ExperienceReservation;
import tour.nonghaeng.domain.reservation.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReservationResponseDto extends ReservationResponseDto {
    private final Long experienceReservationId;
    private final String experienceName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int numOfParticipant;
    private final int finalPrice;

    @Builder
    public ExpReservationResponseDto(Long experienceReservationId, String experienceName, LocalDate reservationDate, LocalTime startTime, LocalTime endTime, int numOfParticipant, int finalPrice, RequestPaymentDto paymentDto) {
        super(paymentDto);
        this.experienceReservationId = experienceReservationId;
        this.experienceName = experienceName;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numOfParticipant = numOfParticipant;
        this.finalPrice = finalPrice;
    }


    public static ExpReservationResponseDto toDto(ExperienceReservation experienceReservation) {
        return ExpReservationResponseDto.builder()
                .experienceReservationId(experienceReservation.getId())
                .experienceName(experienceReservation.getExperience().getExperienceName())
                .reservationDate(experienceReservation.getReservationDate())
                .startTime(experienceReservation.getExperienceRound().getStartTime())
                .endTime(experienceReservation.getExperienceRound().getEndTime())
                .numOfParticipant(experienceReservation.getNumOfParticipant())
                .finalPrice(experienceReservation.getPrice())
                .build();
    }
}
