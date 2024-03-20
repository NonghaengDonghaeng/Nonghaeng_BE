package tour.nonghaeng.global.reservation;

import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;

import java.time.LocalDate;

public class TestExperienceReservation {

    public static final ReservationStateType EXPERIENCE_RESERVATION_STATE_TYPE = ReservationStateType.WAITING_RESERVATION;
    public static final int EXPERIENCE_RESERVATION_PRICE = 1000;
    public static final int EXPERIENCE_RESERVATION_NUM_OF_PARTICIPANT = 2;
    private static final String EXPERIENCE_RESERVATION_NAME = "king";
    private static final String EXPERIENCE_RESERVATION_NUMBER = "010-9876-9876";
    private static final String EXPERIENCE_RESERVATION_EMAIL = "king@email.com";

    public static ExperienceReservation makeTestExperienceReservation(User user, ExperienceRound experienceRound,LocalDate reservationDate) {
        return ExperienceReservation.builder()
                .user(user)
                .experienceRound(experienceRound)
                .stateType(EXPERIENCE_RESERVATION_STATE_TYPE)
                .price(EXPERIENCE_RESERVATION_PRICE)
                .reservationDate(reservationDate)
                .numOfParticipant(EXPERIENCE_RESERVATION_NUM_OF_PARTICIPANT)
                .number(EXPERIENCE_RESERVATION_NUMBER)
                .reservationName(EXPERIENCE_RESERVATION_NAME)
                .email(EXPERIENCE_RESERVATION_EMAIL)
                .build();
    }


    public static ExperienceReservation makeTestExperienceReservation(User user, ExperienceRound experienceRound,LocalDate reservationDate,int numOfParticipant) {
        return ExperienceReservation.builder()
                .user(user)
                .experienceRound(experienceRound)
                .stateType(EXPERIENCE_RESERVATION_STATE_TYPE)
                .price(EXPERIENCE_RESERVATION_PRICE)
                .reservationDate(reservationDate)
                .numOfParticipant(numOfParticipant)
                .number(EXPERIENCE_RESERVATION_NUMBER)
                .reservationName(EXPERIENCE_RESERVATION_NAME)
                .email(EXPERIENCE_RESERVATION_EMAIL)
                .build();
    }
}
