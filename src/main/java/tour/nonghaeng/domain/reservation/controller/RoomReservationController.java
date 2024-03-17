package tour.nonghaeng.domain.reservation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationResponseDto;
import tour.nonghaeng.domain.reservation.service.RoomReservationService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.reservation.RoomReservationValidator;

@RestController
@RequestMapping("/reservations/room")
@RequiredArgsConstructor
@Slf4j
public class RoomReservationController {

    private final AuthService authService;
    private final RoomReservationService roomReservationService;

    private final RoomReservationValidator roomReservationValidator;


    //숙소예약
    @PostMapping
    public ResponseEntity<RoomReservationResponseDto> createRoomReservation(Authentication authentication,
                                                                            @RequestBody CreateRoomReservationDto requestDto) {

        User user = authService.toUserEntity(authentication);

        RoomReservationResponseDto responseDto = roomReservationService.createRoomReservation(user, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
