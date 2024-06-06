package tour.nonghaeng.domain.reservation.service;

import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.ReservationCancelResponseDto;
import tour.nonghaeng.domain.reservation.dto.ReservationDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationPersonInfo;
import tour.nonghaeng.global.infra.service.CrudService;

public interface ReservationService extends CrudService<Reservation, CreateReservationDto>, ViewReservationSummaryDtoByTypeService {


    ReservationDetailDto getReservationDetailDto(Member member, Long reservationId);

    ReservationPersonInfo getReservationPersonInfo(Member user);

    //예약 취소,승인
    ReservationCancelResponseDto cancelReservation(Member user, Long reservationId);

    Long approveReservation(Long reservationId, boolean notApproveFlag);

    String findTypeById(Long reservationId);


    //스케줄링
    void autoChangeCompleteReservation();

    void autoChangeCancelReservation();


}
