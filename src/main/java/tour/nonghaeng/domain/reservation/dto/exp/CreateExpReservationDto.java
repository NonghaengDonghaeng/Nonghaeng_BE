package tour.nonghaeng.domain.reservation.dto.exp;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.data.ExperienceReservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;

import java.time.LocalDate;

@JsonTypeName("experience")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateExpReservationDto extends CreateReservationDto {
    private Long roundId;                   //예약 회차
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;      //예약 날짜
    private int numOfParticipant;           //참여인원
    private String reservationName;         //예약자명
    private String number;                  //연락처
    private String email;                   //이메일
    private int finalPrice;                 //최종가격

    @Builder
    public CreateExpReservationDto(Long roundId, LocalDate reservationDate, int numOfParticipant, String reservationName, String number, String email, int finalPrice) {
        this.roundId = roundId;
        this.reservationDate = reservationDate;
        this.numOfParticipant = numOfParticipant;
        this.reservationName = reservationName;
        this.number = number;
        this.email = email;
        this.finalPrice = finalPrice;
    }


    public ExperienceReservation toEntity(User user, ExperienceRound experienceRound) {
        return ExperienceReservation.builder()
                .user(user)
                .experienceRound(experienceRound)
                .stateType(ReservationStateType.WAITING_RESERVATION)
                .price(this.getFinalPrice())
                .reservationDate(this.getReservationDate())
                .numOfParticipant(this.getNumOfParticipant())
                .reservationName(this.getReservationName())
                .number(this.getNumber())
                .email(this.getEmail())
                .build();
    }

}
