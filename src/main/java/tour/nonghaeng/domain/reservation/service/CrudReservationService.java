package tour.nonghaeng.domain.reservation.service;

import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.global.infra.service.CrudService;

public interface CrudReservationService extends CrudService<Reservation, CreateReservationDto> {

    ReservationServiceType getType();
}
