package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.photo.service.PhotoService;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class PhotoController {

    private final PhotoService photoService;
    private final AuthService authService;

    private final PhotoValidator photoValidator;

    @DeleteMapping("/seller/{photoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("photoId") Long photoId) {
        //TODO: 소유자인지 확인하는 과정 추가하기

        photoService.delete(photoId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }
}
