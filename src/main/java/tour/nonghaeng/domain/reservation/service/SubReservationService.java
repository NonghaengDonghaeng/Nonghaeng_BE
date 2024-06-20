package tour.nonghaeng.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.global.infra.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;
import tour.nonghaeng.global.infra.service.CrudService;

import java.time.LocalDate;

public interface SubReservationService
        <SubReservation extends Reservation, Entity, DtoType extends CreateReservationDto<SubReservation,Entity>>
        extends CrudService<Reservation, DtoType> {

    ReservationServiceType getType();

    void deleteTmpReservation(Long reservationId);


    Page<? extends ReservationSummaryDto> getReservationSummaryDtoPage(Member member, Pageable pageable);


    LocalDate findStartDateById(Long roomReservationId);

    LocalDate findEndDateById(Long roomReservationId);

    int countRemain(Entity entity, LocalDate date);


}
