package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.service.ReviewPhotoService;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;
import tour.nonghaeng.domain.photo.valid.ReviewPhotoValidator;
import tour.nonghaeng.domain.review.valid.ReviewValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/image/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewPhotoController {

    private final ReviewPhotoService reviewPhotoService;
    private final AuthService authService;

    private final ReviewValidator reviewValidator;
    private final ReviewPhotoValidator reviewPhotoValidator;
    private final PhotoValidator photoValidator;

    @PostMapping("/upload/{reviewId}")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile imageFile,
                                         @PathVariable("reviewId") Long reviewId) {

        User user = authService.toUserEntity(authentication);

        reviewValidator.ownerValidate(user, reviewId);

        Long uploadId = reviewPhotoService.upload(user, reviewId, imageFile);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
    }

    @GetMapping("/list/{reviewId}")
    public ResponseEntity<List<PhotoInfoDto>> showAllImageList(@PathVariable("reviewId") Long reviewId) {

        List<PhotoInfoDto> roomPhotoInfoListDto = reviewPhotoService.getReviewPhotoInfoListDto(reviewId);

        return new ResponseEntity<>(roomPhotoInfoListDto, HttpStatus.OK);
    }

    @GetMapping("/seller/representative/{reviewPhotoId}")
    public ResponseEntity<String> changeRepresentativePhoto(Authentication authentication,
                                                            @PathVariable("reviewPhotoId") Long reviewPhotoId) {

        reviewPhotoValidator.ownerValidate(authService.toUserEntity(authentication), reviewPhotoId);

        reviewPhotoService.changeRepresentativePhoto(reviewPhotoId);

        return new ResponseEntity<>("대표사진 설정 완료", HttpStatus.OK);
    }
}
