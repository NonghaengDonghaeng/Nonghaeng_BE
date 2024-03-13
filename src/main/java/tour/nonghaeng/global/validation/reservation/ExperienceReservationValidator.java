package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.reservation.dto.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.experience.ExperienceOpenDateValidator;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExperienceReservationValidator {

    private final ExperienceReservationRepository experienceReservationRepository;

    private final ExperienceOpenDateValidator experienceOpenDateValidator;

    public void createExpReservationDtoValidate(ExperienceRound experienceRound, int currentRemainParticipant, CreateExpReservationDto dto) {

        log.info(Integer.toString(currentRemainParticipant));
        //가격확인
        if (dto.getFinalPrice() != experienceRound.getExperience().getPrice() * dto.getNumOfParticipant()) {
            throw new ReservationException(ReservationErrorCode.WRONG_FINAL_PRICE_ERROR);
        }
        //운영날짜 확인
        experienceOpenDateValidator.dateParameterValidate(experienceRound.getExperience(),dto.getReservationDate());

        // 인원이 충분한지
        if (currentRemainParticipant < dto.getNumOfParticipant()) {
            throw new ReservationException(ReservationErrorCode.EXCEEDED_PARTICIPANT);
        }

    }

    public void idValidate(Long experienceReservationId) {
        if (!experienceReservationRepository.existsById(experienceReservationId)) {
            throw new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID);
        }
    }
}
