package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.service.ReviewPhotoService;
import tour.nonghaeng.domain.photo.valid.ReviewPhotoValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/image/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewPhotoController {

    private final ReviewPhotoService reviewPhotoService;
    private final AuthService authService;

    private final ReviewPhotoValidator reviewPhotoValidator;




    @PostMapping("/uploads/{reviewId}")
    public ResponseEntity<String> uploads(Authentication authentication,
                                         @RequestPart("images") List<MultipartFile> imageFiles,
                                         @PathVariable("reviewId") Long reviewId) {

        Member user = authService.toMemberEntity(authentication);

        reviewPhotoService.uploads(user, reviewId, imageFiles);

        return new ResponseEntity<>("images 업로드 완료.", HttpStatus.CREATED);
    }


    @GetMapping("/list/{id}")
    public ResponseEntity<List<PhotoInfoDto>> showAllImageList(@PathVariable("id") Long id) {

        List<PhotoInfoDto> dtoList = reviewPhotoService.getPhotoInfoListDto( id);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/representative/{reviewPhotoId}")
    public ResponseEntity<String> changeRepresentativePhoto(Authentication authentication,
                                                            @PathVariable("reviewPhotoId") Long reviewPhotoId) {

        Member user = authService.toMemberEntity(authentication);

        reviewPhotoValidator.ownerValidate(user, reviewPhotoId);

        reviewPhotoService.changeRepresentativePhoto(user,reviewPhotoId);

        return new ResponseEntity<>("대표사진 설정 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("photoId") Long photoId) {

        Member user = authService.toMemberEntity(authentication);

        reviewPhotoService.delete(user,photoId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }
}
