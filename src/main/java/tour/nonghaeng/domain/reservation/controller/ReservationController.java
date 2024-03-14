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
import tour.nonghaeng.domain.reservation.dto.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.dto.ExpReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ExperienceReservationService experienceReservationService;
    private final AuthService authService;

    //1. 체험예약

    //체험 예약하기
    @PostMapping("/experience")
    public ResponseEntity<ExpReservationResponseDto> reserveExperience(Authentication authentication,
                                                                       @RequestBody CreateExpReservationDto requestDto) {
        User user = authService.toUserEntity(authentication);

        ExpReservationResponseDto responseDto = experienceReservationService.reserveExperience(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //관리자용 내 예약보기
    @GetMapping("/seller/experience/my-reservation")
    public ResponseEntity<Page<ExpReservationSellerSummaryDto>> showMyReservation(Authentication authentication, @PageableDefault(size = 20) Pageable pageable) {

        Seller seller = authService.toSellerEntity(authentication);

        Page<ExpReservationSellerSummaryDto> dtoPage = experienceReservationService.showExpReservationSummaryList(seller, pageable);


        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    //TODO: 관리자 API 예약 확인, 예약승인 / 소비자가 예약취소, 내 예약보기 등
}

