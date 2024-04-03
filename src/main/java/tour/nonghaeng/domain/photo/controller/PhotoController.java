package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.photo.service.PhotoService;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class PhotoController {

    private final PhotoService photoService;
}
