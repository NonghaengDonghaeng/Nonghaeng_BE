package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.service.TourPhotoService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/image/tours")
@RequiredArgsConstructor
@Slf4j
public class TourPhotoController {

    private final TourPhotoService tourPhotoService;
    private final AuthService authService;
    
    @PostMapping("/seller/upload")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile image) {

        Seller seller = authService.toSellerEntity(authentication);

        Long uploadId = tourPhotoService.upload(seller, image);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
    }

    @DeleteMapping("/seller/{tourPhotoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("tourPhotoId") Long tourPhotoId) {

        Seller seller = authService.toSellerEntity(authentication);

        tourPhotoService.delete(seller, tourPhotoId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }
}
