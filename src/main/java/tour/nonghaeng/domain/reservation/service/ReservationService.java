package tour.nonghaeng.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.payment.PortOneResponseDto;
import tour.nonghaeng.global.infra.service.CrudService;

public interface ReservationService extends CrudService<Reservation, CreateReservationDto<?,?>>{


    PortOneResponseDto paymentValid(String paymentUid);

    boolean delete(String paymentUid);


    //조회(ViewService 상속안한 이유는 기본 메소드랑 살짝 다름)
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

    //리뷰 작성 후 저장
    void setWrittenReview(Reservation reservation);

}
