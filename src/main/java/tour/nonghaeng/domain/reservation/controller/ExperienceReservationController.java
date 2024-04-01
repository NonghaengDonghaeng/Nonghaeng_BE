package tour.nonghaeng.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationCancelResponseDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.reservation.ExperienceReservationValidator;
import tour.nonghaeng.global.validation.reservation.ReservationValidator;

@RestController
@RequestMapping("/reservations/experience")
@RequiredArgsConstructor
@Slf4j
public class ExperienceReservationController {

    private final AuthService authService;
    private final ExperienceReservationService experienceReservationService;

    private final ExperienceReservationValidator experienceReservationValidator;
    private final ReservationValidator reservationValidator;

    //1. 체험예약
    //체험 예약하기
    @PostMapping
    public ResponseEntity<ExpReservationResponseDto> createExpReservation(Authentication authentication,
                                                                          @RequestBody CreateExpReservationDto requestDto) {

        User user = authService.toUserEntity(authentication);

        ExpReservationResponseDto responseDto = experienceReservationService.createExpReservation(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //소비자 내 체험예약 취소하기
    @GetMapping("/cancel/{reservationId}")
    public ResponseEntity<ExpReservationCancelResponseDto> cancelExpReservation(Authentication authentication,
                                                                                @PathVariable("reservationId") Long experienceReservationId) {

        User user = authService.toUserEntity(authentication);

        reservationValidator.ownerUserValidate(user, experienceReservationId);

        ExpReservationCancelResponseDto dto =
                experienceReservationService.cancelExpReservation(user, experienceReservationId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}

