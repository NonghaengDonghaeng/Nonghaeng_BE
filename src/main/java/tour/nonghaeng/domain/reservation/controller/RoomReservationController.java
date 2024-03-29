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
import tour.nonghaeng.domain.reservation.dto.room.*;
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

    //소비자 내 체험예약 리스트 보기
    @GetMapping("/my-reservation")
    public ResponseEntity<Page<RoomReservationUserSummaryDto>> showMyRoomReservationUser(Authentication authentication,
                                                                                         @PageableDefault(size = 20) Pageable pageable) {

        User user = authService.toUserEntity(authentication);

        Page<RoomReservationUserSummaryDto> dtoPage =
                roomReservationService.getRoomReservationUserSummaryDtoPage(user, pageable);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    //관리자 API : 관리자용 내 예약요약 보기
    @GetMapping("/seller/my-reservation")
    public ResponseEntity<Page<RoomReservationSellerSummaryDto>> showMyRoomReservationSeller(Authentication authentication,
                                                                                             @PageableDefault(size = 20) Pageable pageable) {

        Seller seller = authService.toSellerEntity(authentication);

        Page<RoomReservationSellerSummaryDto> dtoPage =
                roomReservationService.getRoomReservationSellerSummaryDtoPage(seller, pageable);

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<RoomReservationUserDetailDto> showRoomReservationUserDetailDto(Authentication authentication,
                                                                                         @PathVariable("reservationId") Long reservationId) {
        User user = authService.toUserEntity(authentication);

        roomReservationValidator.ownerUserValidate(user, reservationId);

        return new ResponseEntity<>(roomReservationService.getRoomReservationUserDetailDto(reservationId), HttpStatus.OK);
    }

    @GetMapping("/seller/{reservationId}")
    public ResponseEntity<RoomReservationSellerDetailDto> showRoomReservationSellerDetailDto(Authentication authentication,
                                                                                             @PathVariable("reservationId") Long reservationId) {
        Seller seller = authService.toSellerEntity(authentication);

        roomReservationValidator.ownerSellerValidate(seller, reservationId);

        return new ResponseEntity<>(roomReservationService.getRoomReservationSellerDetailDto(reservationId), HttpStatus.OK);
    }

    @GetMapping("/cancel/{reservationId}")
    public ResponseEntity<RoomReservationCancelResponseDto> cancelRoomReservation(Authentication authentication,
                                                                                  @PathVariable("reservationId") Long roomReservationId) {

        User user = authService.toUserEntity(authentication);

        roomReservationValidator.ownerUserValidate(user, roomReservationId);

        RoomReservationCancelResponseDto dto =
                roomReservationService.cancelRoomReservation(user, roomReservationId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/seller/approve/{reservationId}")
    public ResponseEntity<String> approveRoomReservation(Authentication authentication,
                                                         @PathVariable("reservationId") Long roomReservationId,
                                                         @RequestParam(name = "not", defaultValue = "false") boolean notApproveFlag) {

        Seller seller = authService.toSellerEntity(authentication);

        roomReservationValidator.ownerSellerValidate(seller, roomReservationId);

        Long id = roomReservationService.approveRoomReservation(roomReservationId, notApproveFlag);

        if (notApproveFlag == true) {
            return new ResponseEntity<>("체험예약 미승인 완료", HttpStatus.OK);
        }

        return new ResponseEntity<>("체험예약 승인 완료(체험예약 id:" + id + ")", HttpStatus.OK);
    }
}
