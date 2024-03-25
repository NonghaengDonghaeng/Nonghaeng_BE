package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.photo.service.ExperiencePhotoService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/image/experiences")
@RequiredArgsConstructor
@Slf4j
public class ExperiencePhotoController {

    private final ExperiencePhotoService experiencePhotoService;
    private final AuthService authService;
}
