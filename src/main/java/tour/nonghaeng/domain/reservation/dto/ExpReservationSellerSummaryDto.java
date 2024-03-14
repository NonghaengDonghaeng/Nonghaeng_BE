package tour.nonghaeng.domain.reservation.dto;

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
public class ExpReservationSellerSummaryDto {
    private String reservationState;
    private String experienceName;
    private Long experienceReservationId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;
    private int price;
    private int numOfParticipant;

    //TODO: 체험 회차정보도 추가하기


    @Builder
    public ExpReservationSellerSummaryDto(String reservationState, String experienceName, Long experienceReservationId, LocalDate reservationDate, int price, int numOfParticipant) {
        this.reservationState = reservationState;
        this.experienceName = experienceName;
        this.experienceReservationId = experienceReservationId;
        this.reservationDate = reservationDate;
        this.price = price;
        this.numOfParticipant = numOfParticipant;
    }

    public static Page<ExpReservationSellerSummaryDto> toPageDto(Page<ExperienceReservation> experienceReservationPage) {
        return experienceReservationPage.map(experienceReservation -> ExpReservationSellerSummaryDto.builder()
                .reservationState(experienceReservation.getStateType().getName())
                .experienceName(experienceReservation.getExperience().getExperienceName())
                .experienceReservationId(experienceReservation.getId())
                .reservationDate(experienceReservation.getReservationDate())
                .price(experienceReservation.getPrice())
                .numOfParticipant(experienceReservation.getNumOfParticipant())
                .build());

    }
}
