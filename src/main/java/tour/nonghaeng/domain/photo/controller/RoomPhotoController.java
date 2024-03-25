package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.photo.service.RoomPhotoService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/image/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomPhotoController {

    private final RoomPhotoService roomPhotoService;
    private final AuthService authService;
}
