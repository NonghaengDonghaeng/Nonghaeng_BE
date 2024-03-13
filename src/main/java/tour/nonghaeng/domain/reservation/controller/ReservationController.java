package tour.nonghaeng.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping(name = "/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ExperienceReservationService experienceReservationService;
    private final AuthService authService;
}
