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
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.ReservationUserDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.reservation.ReservationValidator;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final AuthService authService;
    private final ReservationService reservationService;

    private final ReservationValidator reservationValidator;

    //파라미터 type=room
    @GetMapping("/my-reservation")
    public ResponseEntity<Page<? extends ReservationUserSummaryDto>> showMyReservationUser(Authentication authentication,
                                                                                           @PageableDefault(size = 20) Pageable pageable,
                                                                                           @RequestParam(value = "type", defaultValue = "room") String type) {
        User user = authService.toUserEntity(authentication);

        Page<? extends ReservationUserSummaryDto> dtoPage =
                reservationService.getReservationUserSummaryDtoPage(user, pageable, type);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<? extends ReservationUserDetailDto> showReservationUserDatailDto(Authentication authentication,
                                                                                           @PathVariable("reservationId") Long reservationId) {

        User user = authService.toUserEntity(authentication);

        reservationValidator.ownerUserValidate(user, reservationId);

        return new ResponseEntity<>(reservationService.getReservationUserDetailDto(reservationId), HttpStatus.OK);
    }
}
