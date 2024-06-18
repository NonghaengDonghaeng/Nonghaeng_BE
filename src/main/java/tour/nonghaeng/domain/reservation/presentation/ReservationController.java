package tour.nonghaeng.domain.reservation.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.payment.PortOneResponseDto;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.reservation.service.valid.ReservationValidator;
import tour.nonghaeng.global.auth.AuthService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final AuthService authService;
    private final ReservationService reservationService;

    private final ReservationValidator reservationValidator;



    @PostMapping
    public ResponseEntity<? extends ReservationResponseDto> create(Authentication authentication,
                                                                   @RequestBody CreateReservationDto<?,?> requestDto) {
        Member user = authService.toMemberEntity(authentication);

        Reservation reservation = reservationService.create(user, requestDto);

        return new ResponseEntity<>(reservation.toReservationResponseDto(), HttpStatus.OK);
    }

    //임시 예약 데이터 삭제
    @DeleteMapping("/{paymentUid}")
    public ResponseEntity<Boolean> deleteTmpReservation(Authentication authentication,
                                                       @PathVariable(value = "paymentUid") String paymentUid) {

        boolean isDelete = reservationService.delete(paymentUid);

        return new ResponseEntity<>(isDelete,HttpStatus.OK);
    }

    //사후 검증
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<PortOneResponseDto> valid(@PathVariable("paymentId")String paymentId){
        PortOneResponseDto response = reservationService.paymentValid(paymentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //소비자,판매자 내 체험/숙소 예약 리스트보기 만약 타입이 안오면 전체로
    //파라미터 type=room,experience,all
    @GetMapping("/my-reservation")
    public ResponseEntity<Page<? extends ReservationSummaryDto>> showMyReservationUser(Authentication authentication,
                                                                                       @PageableDefault(size = 10) Pageable pageable,
                                                                                       @RequestParam(value = "type", defaultValue = "all", required = false) String type) {

        Member member = authService.toMemberEntity(authentication);

        Page<? extends ReservationSummaryDto> dtoPage =
                reservationService.getReservationSummaryDtoPage(member, pageable, type);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    // 내 체험/숙소 예약 상세보기
    @GetMapping("/{reservationId}")
    public ResponseEntity<? extends ReservationDetailDto> showReservationUserDetailDto(Authentication authentication,
                                                                                       @PathVariable("reservationId") Long reservationId) {

        Member member = authService.toMemberEntity(authentication);

        ReservationDetailDto detailDto =
                reservationService.getReservationDetailDto(member, reservationId);

        return new ResponseEntity<>(detailDto, HttpStatus.OK);
    }

    @GetMapping("/reservation-person-info")
    public ResponseEntity<ReservationPersonInfo> getReservationPersonInfo(Authentication authentication) {

        Member user = authService.toMemberEntity(authentication);

        ReservationPersonInfo responseDto = reservationService.getReservationPersonInfo(user);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



    @GetMapping("/seller/approve/{reservationId}")
    public ResponseEntity<String> approveReservation(Authentication authentication,
                                                     @PathVariable("reservationId") Long reservationId,
                                                     @RequestParam(name = "not", defaultValue = "false") boolean notApproveFlag) {

        Member seller = authService.toMemberEntity(authentication);

        reservationValidator.ownerSellerValidate(seller, reservationId);

        Long id = reservationService.approveReservation(reservationId, notApproveFlag);

        if (notApproveFlag) {
            return new ResponseEntity<>("예약 미승인 완료(예약 id:" + id + ")", HttpStatus.OK);
        }

        return new ResponseEntity<>("예약 승인 완료(예약 id:" + id + ")", HttpStatus.OK);
    }

    @GetMapping("/cancel/{reservationId}")
    public ResponseEntity<? extends ReservationCancelResponseDto> cancelReservation(Authentication authentication,
                                                                                    @PathVariable("reservationId") Long reservationId) {

        Member user = authService.toMemberEntity(authentication);

        reservationValidator.ownerUserValidate(user, reservationId);

        return new ResponseEntity<>(reservationService.cancelReservation(user, reservationId), HttpStatus.OK);
    }
}
