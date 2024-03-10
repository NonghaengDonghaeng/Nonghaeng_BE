package tour.nonghaeng.domain.room.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.CreateRoomDto;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.auth.service.AuthService;

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
}
