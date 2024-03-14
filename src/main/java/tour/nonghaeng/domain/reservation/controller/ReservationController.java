package tour.nonghaeng.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.*;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.reservation.ExperienceReservationValidator;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ExperienceReservationService experienceReservationService;
    private final AuthService authService;

    private final ExperienceReservationValidator experienceReservationValidator;

    //1. 체험예약

    //체험 예약하기
    @PostMapping("/experience")
    public ResponseEntity<ExpReservationResponseDto> reserveExperience(Authentication authentication,
                                                                       @RequestBody CreateExpReservationDto requestDto) {
        User user = authService.toUserEntity(authentication);

        ExpReservationResponseDto responseDto = experienceReservationService.reserveExperience(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //관리자 API : 관리자용 내 예약보기
    @GetMapping("/seller/experience/my-reservation")
    public ResponseEntity<Page<ExpReservationSellerSummaryDto>> showMyReservation(Authentication authentication, @PageableDefault(size = 20) Pageable pageable) {

        Seller seller = authService.toSellerEntity(authentication);

        Page<ExpReservationSellerSummaryDto> dtoPage = experienceReservationService.showExpReservationSummaryList(seller, pageable);


        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    //관리자 API : 예약대기를 예약 승인하기, 혹은 미승인하기(파라미터로 not = true인 경우 미승인)
    //승인을 다시 미승인인 경우는 아직 배제
    @GetMapping("/seller/experience/approve/{reservationId}")
    public ResponseEntity<String> approveExpReservation(Authentication authentication,
                                                        @PathVariable("reservationId") Long reservationId,
                                                        @RequestParam(name = "not", defaultValue = "false") boolean notApproveFlag) {
        Seller seller = authService.toSellerEntity(authentication);

        experienceReservationValidator.ownerSellerValidate(seller, reservationId);

        Long id = experienceReservationService.approveExpReservation(reservationId, notApproveFlag);

        if (notApproveFlag == true) {
            return new ResponseEntity<>("체험예약 미승인완료(체험예약 id:" + id + ")", HttpStatus.OK);
        }
        return new ResponseEntity<>("체험예약 승인완료(체험예약 id:" + id + ")", HttpStatus.OK);
    }

    //관리자 API : 체험예약에 대한 상세정보 보기
    @GetMapping("/seller/experience/{reservationId}")
    public ResponseEntity<ExpReservationSellerDetailDto> showExpReservationDetailDto(Authentication authentication,
                                                                                     @PathVariable("reservationId") Long reservationId) {
        Seller seller = authService.toSellerEntity(authentication);

        experienceReservationValidator.ownerSellerValidate(seller, reservationId);

        return new ResponseEntity<>(experienceReservationService.getExpReservationSellerDetailDto(reservationId), HttpStatus.OK);
    }

    //소비자 내 체험예약 리스트 보기

    //소비자 내 체험예약 상세보기

    //소비자 내 체험예약 취소하기
    @GetMapping("/experience/cancel/{experienceReservationId}")
    public ResponseEntity<ExpReservationCancelResponseDto> cancelExpReservation(Authentication authentication,
                                                       @PathVariable("reservationId") Long experienceReservationId) {
        User user = authService.toUserEntity(authentication);

        experienceReservationValidator.ownerUserValidate(user, experienceReservationId);

        ExpReservationCancelResponseDto dto = experienceReservationService.cancelExpReservation(user, experienceReservationId);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    //TODO: 소비자가 예약취소, 내 예약보기 등
}

