package tour.nonghaeng.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;

import java.time.LocalDate;

public interface SubReservationEntityService<T> extends CrudService<Reservation, CreateReservationDto> {

    ReservationServiceType getType();

    Page<? extends ReservationSummaryDto> getReservationSummaryDtoPage(Member member, Pageable pageable);


    LocalDate findStartDateById(Long roomReservationId);

    LocalDate findEndDateById(Long roomReservationId);

    int countRemain(T t, LocalDate date);
}
