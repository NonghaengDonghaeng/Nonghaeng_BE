package tour.nonghaeng.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.global.infra.service.CrudService;

public interface ReservationService extends CrudService<Reservation, CreateReservationDto>{

    //조회
    ReservationDetailDto getReservationDetailDto(Member member, Long reservationId);

    Page<? extends ReservationSummaryDto> getReservationSummaryDtoPage(Member member, Pageable pageable,String type);

    ReservationPersonInfo getReservationPersonInfo(Member user);


    // 취소,승인
    ReservationCancelResponseDto cancelReservation(Member user, Long reservationId);

    Long approveReservation(Long reservationId, boolean notApproveFlag);

    String findTypeById(Long reservationId);


    //스케줄링
    void autoChangeCompleteReservation();

    void autoChangeCancelReservation();


}
