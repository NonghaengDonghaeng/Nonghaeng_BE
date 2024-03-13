package tour.nonghaeng.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.ExpReservationResponseDto;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ExperienceReservationService experienceReservationService;
    private final AuthService authService;

    //체험 예약하기
    @PostMapping("/experience")
    public ResponseEntity<ExpReservationResponseDto> reserveExperience(Authentication authentication,
                                                                       @RequestBody CreateExpReservationDto requestDto) {
        log.info("he");
        User user = authService.toUserEntity(authentication);

        ExpReservationResponseDto responseDto = experienceReservationService.reserveExperience(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}

