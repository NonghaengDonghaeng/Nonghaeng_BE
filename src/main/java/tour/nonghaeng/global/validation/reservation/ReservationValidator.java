package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.repo.ReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationValidator {

    private final ReservationRepository reservationRepository;


    public void ownerSellerValidate(Seller seller, Long reservationId) {

        idValidate(reservationId);

        if (!seller.equals(reservationRepository.findSellerById(reservationId).get())) {
            throw new ReservationException(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void ownerUserValidate(User user, Long reservationId) {

        idValidate(reservationId);

        if (!user.equals(reservationRepository.findUserById(reservationId).get())) {
            throw new ReservationException(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void checkWaitingState(Reservation reservation) {

        if (!reservation.getStateType().equals(ReservationStateType.WAITING_RESERVATION)) {
            throw new ReservationException(ReservationErrorCode.NOT_WAITING_RESERVATION_STATE);
        }
    }

    public void checkCancelState(Reservation reservation) {

        ReservationStateType stateType = reservation.getStateType();

        if (stateType.equals(ReservationStateType.CANCEL_RESERVATION)
                || stateType.equals(ReservationStateType.COMPLETE_RESERVATION)
                || stateType.equals(ReservationStateType.NOT_CONFIRM_RESERVATION)) {

            throw new ReservationException(ReservationErrorCode.CANT_CANCEL_RESERVATION_STATE);
        }
    }

    public void idValidate(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ReservationException(ReservationErrorCode.NO_EXIST_RESERVATION_ID);
        }
    }

    public void checkPointValidate(User user, int price) {

        if (user.getPoint() < price) {
            throw new ReservationException(ReservationErrorCode.NOT_ENOUGH_POINT_ERROR);
        }
    }

    public void pageValidate(Page<? extends Reservation> page) {

        if (page.isEmpty()) {
            throw new ReservationException(ReservationErrorCode.NO_RESERVATION_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }
}
