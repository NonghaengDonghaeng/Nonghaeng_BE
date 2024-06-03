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
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationResponseDto;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.reservation.service.valid.ReservationValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final AuthService authService;
    private final ReservationService reservationService;

    private final ReservationValidator reservationValidator;


    @PostMapping("/experience")
    public ResponseEntity<ExpReservationResponseDto> createExpReservation(Authentication authentication,
                                                                          @RequestBody CreateExpReservationDto requestDto) {

        Member user = authService.toMemberEntity(authentication);

        ExpReservationResponseDto responseDto = reservationService.createExpReservation(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/room")
    public ResponseEntity<RoomReservationResponseDto> createRoomReservation(Authentication authentication,
                                                                            @RequestBody CreateRoomReservationDto requestDto) {

        Member user = authService.toMemberEntity(authentication);

        RoomReservationResponseDto responseDto = reservationService.createRoomReservation(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/reservation-person-info")
    public ResponseEntity<ReservationPersonInfo> getReservationPersonInfo(Authentication authentication) {

        Member user = authService.toMemberEntity(authentication);

        ReservationPersonInfo responseDto = reservationService.getReservationPersonInfo(user);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //소비자 내 체험/숙소 예약 리스트보기 만약 타입이 안오면 전체로
    //파라미터 type=room,exp
    @GetMapping("/my-reservation")
    public ResponseEntity<Page<? extends ReservationUserSummaryDto>> showMyReservationUser(Authentication authentication,
                                                                                           @PageableDefault(size = 10) Pageable pageable,
                                                                                           @RequestParam(value = "type", defaultValue = "all", required = false) String type) {

        Member user = authService.toMemberEntity(authentication);

        Page<? extends ReservationUserSummaryDto> dtoPage =
                reservationService.getReservationUserSummaryDtoPage(user, pageable, type);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    //소비자 내 체험/숙소 예약 상세보기
    @GetMapping("/{reservationId}")
    public ResponseEntity<? extends ReservationUserDetailDto> showReservationUserDetailDto(Authentication authentication,
                                                                                           @PathVariable("reservationId") Long reservationId) {

        Member user = authService.toMemberEntity(authentication);

        reservationValidator.ownerUserValidate(user, reservationId);

        return new ResponseEntity<>(reservationService.getReservationUserDetailDto(reservationId), HttpStatus.OK);
    }

    //관리자 API : 관리자용 내 예약요약 보기
    @GetMapping("/seller/my-reservation")
    public ResponseEntity<Page<? extends ReservationSellerSummaryDto>> showMyReservationSeller(Authentication authentication,
                                                                                               @PageableDefault(size = 20) Pageable pageable,
                                                                                               @RequestParam(value = "type", defaultValue = "room") String type) {

        Member seller = authService.toMemberEntity(authentication);

        Page<? extends ReservationSellerSummaryDto> dtoPage =
                reservationService.getReservationSellerSummaryDtoPage(seller, pageable, type);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    //관리자 API : 관리자용 내 예약상세 보기
    @GetMapping("/seller/{reservationId}")
    public ResponseEntity<? extends ReservationSellerDetailDto> showReservationSellerDetailDto(Authentication authentication,
                                                                                               @PathVariable("reservationId") Long reservationId) {

        Member seller = authService.toMemberEntity(authentication);

        reservationValidator.ownerSellerValidate(seller, reservationId);

        return new ResponseEntity<>(reservationService.getReservationSellerDetailDto(reservationId), HttpStatus.OK);
    }

    @GetMapping("/seller/approve/{reservationId}")
    public ResponseEntity<String> approveReservation(Authentication authentication,
                                                     @PathVariable("reservationId") Long reservationId,
                                                     @RequestParam(name = "not", defaultValue = "false") boolean notApproveFlag) {

        Member seller = authService.toMemberEntity(authentication);

        reservationValidator.ownerSellerValidate(seller, reservationId);

        Long id = reservationService.approveReservation(reservationId, notApproveFlag);

        if (notApproveFlag) {
            return new ResponseEntity<>("체험예약 미승인 완료", HttpStatus.OK);
        }

        return new ResponseEntity<>("체험예약 승인 완료(체험예약 id:" + id + ")", HttpStatus.OK);
    }

    @GetMapping("/cancel/{reservationId}")
    public ResponseEntity<? extends ReservationCancelResponseDto> cancelReservation(Authentication authentication,
                                                                                    @PathVariable("reservationId") Long reservationId) {

        Member user = authService.toMemberEntity(authentication);

        reservationValidator.ownerUserValidate(user, reservationId);

        return new ResponseEntity<>(reservationService.cancelReservation(user, reservationId), HttpStatus.OK);
    }
}
