package tour.nonghaeng.global.validation.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;

@Component
@RequiredArgsConstructor
public class RoomReservationValidator {

    private final RoomReservationRepository roomReservationRepository;


    public void idValidate(Long roomReservationId) {
        if (!roomReservationRepository.existsById(roomReservationId)) {
            throw new ReservationException(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID);
        }
    }
}
