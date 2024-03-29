package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.experience.ExperienceCloseDateValidator;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExperienceReservationValidator {

    private final ExperienceReservationRepository experienceReservationRepository;

    private final ExperienceCloseDateValidator experienceCloseDateValidator;
    private final ReservationValidator reservationValidator;

    public void experienceReservationValidate(ExperienceRound experienceRound, User user, int currentRemainParticipant, CreateExpReservationDto dto) {

        createExpReservationDtoValidate(experienceRound,currentRemainParticipant,dto);

        reservationValidator.checkPointValidate(user, dto.getFinalPrice());
    }


    public void createExpReservationDtoValidate(ExperienceRound experienceRound, int currentRemainParticipant, CreateExpReservationDto dto) {

        log.info(Integer.toString(currentRemainParticipant));
        //가격확인
        if (dto.getFinalPrice() != experienceRound.getExperience().getPrice() * dto.getNumOfParticipant()) {
            throw new ReservationException(ReservationErrorCode.WRONG_FINAL_PRICE_ERROR);
        }
        //운영날짜 확인
        experienceCloseDateValidator.isOpenDateParameterValidate(experienceRound.getExperience(),dto.getReservationDate());

        // 인원이 충분한지
        if (currentRemainParticipant < dto.getNumOfParticipant()) {
            throw new ReservationException(ReservationErrorCode.EXCEEDED_PARTICIPANT);
        }
    }
}
