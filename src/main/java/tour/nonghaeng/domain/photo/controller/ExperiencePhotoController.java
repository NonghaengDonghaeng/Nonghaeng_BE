package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.service.ExperiencePhotoService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/image/experiences")
@RequiredArgsConstructor
@Slf4j
public class ExperiencePhotoController {

    private final ExperiencePhotoService experiencePhotoService;
    private final AuthService authService;

    @PostMapping("/seller/upload/{experienceId}")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile imageFile,
                                         @PathVariable("experienceId") Long experienceId) {

        Seller seller = authService.toSellerEntity(authentication);

        Long uploadId = experiencePhotoService.upload(seller, experienceId, imageFile);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
    }

    @DeleteMapping("/seller/{experiencePhotoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("experiencePhotoId") Long experiencePhotoId) {

        Seller seller = authService.toSellerEntity(authentication);

        experiencePhotoService.delete(seller, experiencePhotoId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }
}
