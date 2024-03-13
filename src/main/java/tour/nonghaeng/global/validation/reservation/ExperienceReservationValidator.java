package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;

@Component
@RequiredArgsConstructor
public class ExperienceReservationValidator {

    private final ExperienceReservationRepository experienceReservationRepository;

    public void idValidate(Long experienceReservationId) {
        if (!experienceReservationRepository.existsById(experienceReservationId)) {
            throw new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID);
        }
    }
}
