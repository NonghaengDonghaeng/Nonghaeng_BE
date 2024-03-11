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
import tour.nonghaeng.domain.room.dto.CreateRoomDto;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.dto.RoomTourSummaryDto;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.auth.service.AuthService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final AuthService authService;

    //관리자 API: 숙소 등록하기
    @PostMapping("/seller/add")
    public ResponseEntity<String> addRoom(Authentication authentication,
                                          @RequestBody CreateRoomDto createRoomDto) {
        Seller seller = authService.toSellerEntity(authentication);

        Long roomId = roomService.createAndAddRoom(seller, createRoomDto);

        return new ResponseEntity<>("숙소 등록완료, 숙소Id:"+roomId.toString(), HttpStatus.OK);
    }

    //숙소정보 리스트 보기
    @GetMapping
    public ResponseEntity<Page<RoomTourSummaryDto>> findAll(@PageableDefault(size = 5) Pageable pageable) {

        Page<RoomTourSummaryDto> dto = roomService.showTourRoomSummary(pageable);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //숙소 중간페이지, 파라미터로 날짜와 객실수 받기
    @GetMapping("/list/{tourId}")
    public ResponseEntity<List<RoomSummaryDto>> showRoomSummaryList(@PathVariable Long tourId,
                                                             @RequestParam(value = "date",defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate date,
                                                             @RequestParam(value = "num",defaultValue = "1") int num) {
        List<RoomSummaryDto> dtoList = roomService.showRoomSummaryList(tourId, date, num);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
