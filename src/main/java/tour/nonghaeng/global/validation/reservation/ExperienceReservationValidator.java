package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
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


    public void ownerSellerValidate(Seller seller, Long experienceReservationId) {

        idValidate(experienceReservationId);

        if (!seller.equals(experienceReservationRepository.findSellerById(experienceReservationId).get())) {
            throw new ReservationException(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void ownerUserValidate(User user, Long experienceReservationId) {

        idValidate(experienceReservationId);

        if (!user.equals(experienceReservationRepository.findUserById(experienceReservationId).get())) {
            throw new ReservationException(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void experienceReservationValidate(ExperienceRound experienceRound, User user, int currentRemainParticipant, CreateExpReservationDto dto) {

        createExpReservationDtoValidate(experienceRound,currentRemainParticipant,dto);

        checkPointValidate(user, dto);

    }

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

    public void checkWaitingState(ExperienceReservation experienceReservation) {
        if (!experienceReservation.getStateType().equals(ReservationStateType.WAITING_RESERVATION)) {
            throw new ReservationException(ReservationErrorCode.NOT_WAITING_RESERVATION_STATE);
        }
    }

    public void checkPointValidate(User user, CreateExpReservationDto dto) {
        if (user.getPoint() < dto.getFinalPrice()) {
            throw new ReservationException(ReservationErrorCode.NOT_ENOUGH_POINT_ERROR);
        }
    }

    public void pageValidate(Page<ExperienceReservation> page) {
        if (page.isEmpty()) {
            throw new ReservationException(ReservationErrorCode.NO_RESERVATION_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }

    public void idValidate(Long experienceReservationId) {
        if (!experienceReservationRepository.existsById(experienceReservationId)) {
            throw new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID);
        }
    }
}
