package tour.nonghaeng.domain.reservation.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpReservationUserSummaryDto {

    private Long experienceReservationId;
    private String experienceName;
    private String reservationState;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    private int price;
    private int numOfParticipant;

    @Builder
    public ExpReservationUserSummaryDto(Long experienceReservationId, String experienceName, String reservationState, LocalDate reservationDate, int price, int numOfParticipant) {
        this.experienceReservationId = experienceReservationId;
        this.experienceName = experienceName;
        this.reservationState = reservationState;
        this.reservationDate = reservationDate;
        this.price = price;
        this.numOfParticipant = numOfParticipant;
    }

    public static Page<ExpReservationUserSummaryDto> toPageDto(Page<ExperienceReservation> page) {
        return page.map(experienceReservation -> ExpReservationUserSummaryDto.builder()
                .experienceReservationId(experienceReservation.getId())
                .experienceName(experienceReservation.getExperience().getExperienceName())
                .reservationState(experienceReservation.getStateType().getName())
                .reservationDate(experienceReservation.getReservationDate())
                .price(experienceReservation.getPrice())
                .numOfParticipant(experienceReservation.getNumOfParticipant())
                .build());
    }
}
