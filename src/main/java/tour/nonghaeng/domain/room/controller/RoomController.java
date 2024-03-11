package tour.nonghaeng.domain.room.controller;

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
import tour.nonghaeng.domain.room.dto.*;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.RoomValidator;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final AuthService authService;

    private final RoomValidator roomValidator;

    //관리자 API: 숙소 등록하기
    @PostMapping("/seller/add")
    public ResponseEntity<String> addRoom(Authentication authentication,
                                          @RequestBody CreateRoomDto createRoomDto) {
        Seller seller = authService.toSellerEntity(authentication);

        Long roomId = roomService.createAndAddRoom(seller, createRoomDto);

        return new ResponseEntity<>("숙소 등록완료, 숙소Id:" + roomId.toString(), HttpStatus.OK);
    }

    //숙소정보 리스트 보기
    @GetMapping
    public ResponseEntity<Page<RoomTourSummaryDto>> showRoomTourSummaryPage(@PageableDefault(size = 5) Pageable pageable) {

        Page<RoomTourSummaryDto> dto = roomService.showTourRoomSummary(pageable);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //숙소 중간페이지 중 방요약만 조회, 파라미터로 날짜와 객실수 받기
    @GetMapping("/list/{tourId}")
    public ResponseEntity<List<RoomSummaryDto>> showRoomSummaryList(@PathVariable Long tourId,
                                                                    @RequestParam(value = "date", defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate date,
                                                                    @RequestParam(value = "num", defaultValue = "1") int num) {
        List<RoomSummaryDto> dtoList = roomService.showRoomSummaryList(tourId, date, num);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    //숙소 중간페이지 정보 요청
    @GetMapping("/middle-page/{tourId}")
    public ResponseEntity<RoomTourDetailDto> showMiddleRoomTourDetail(@PathVariable Long tourId) {

        RoomTourDetailDto roomTourDetailDto = roomService.getRoomTourDetailDto(tourId);

        return new ResponseEntity<>(roomTourDetailDto, HttpStatus.OK);
    }

    //숙소 상세페이지 보기
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailDto> showRoomDetail(@PathVariable Long roomId,
                                                        @RequestParam(value = "date", defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate requestDate) {
        RoomDetailDto roomDetailDto = roomService.getRoomDetailDto(roomId, requestDate);

        return new ResponseEntity<>(roomDetailDto, HttpStatus.OK);

    }

    //관리자 API: 숙소 닫는 날짜 추가
    @PostMapping("/seller/add-closedate/{roomId}")
    public ResponseEntity<String> addCloseDate(Authentication authentication,
                                               @PathVariable Long roomId,
                                               @RequestBody List<AddRoomCloseDateDto> addRoomCloseDateDtos) {
        roomValidator.ownerValidate(authService.toSellerEntity(authentication),roomId);

        roomService.addOnlyCloseDates(roomId, addRoomCloseDateDtos);

        return new ResponseEntity<>("미운영날짜 등록완료", HttpStatus.OK);
    }
}
