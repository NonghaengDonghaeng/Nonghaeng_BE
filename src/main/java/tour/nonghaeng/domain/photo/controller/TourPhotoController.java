package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.service.TourPhotoService;
import tour.nonghaeng.global.auth.auth.service.AuthService;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;
import tour.nonghaeng.domain.photo.valid.TourPhotoValidator;

import java.util.List;

@RestController
@RequestMapping("/image/tours")
@RequiredArgsConstructor
@Slf4j
public class TourPhotoController {

    private final TourPhotoService tourPhotoService;
    private final AuthService authService;

    private final TourPhotoValidator tourPhotoValidator;
    private final PhotoValidator photoValidator;




    @PostMapping("/seller/uploads")
    public ResponseEntity<String> uploads(Authentication authentication,
                                         @RequestPart("images") List<MultipartFile> imageFiles) {

        Seller seller = authService.toSellerEntity(authentication);

        tourPhotoService.uploads(seller, imageFiles);

        return new ResponseEntity<>("images 업로드 완료.", HttpStatus.CREATED);
    }



    @PostMapping("/seller/upload")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile image) {

        Seller seller = authService.toSellerEntity(authentication);

        Long uploadId = tourPhotoService.upload(seller, image);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
    }



    @GetMapping("/list/{tourId}")
    public ResponseEntity<List<PhotoInfoDto>> showAllImageList(@PathVariable("tourId") Long tourId) {

        List<PhotoInfoDto> tourPhotoInfoListDto = tourPhotoService.getTourPhotoInfoListDto(tourId);

        return new ResponseEntity<>(tourPhotoInfoListDto, HttpStatus.OK);
    }



    @GetMapping("/seller/representative/{tourPhotoId}")
    public ResponseEntity<String> changeRepresentativePhoto(Authentication authentication,
                                                            @PathVariable("tourPhotoId") Long tourPhotoId) {

        tourPhotoValidator.ownerValidate(authService.toSellerEntity(authentication), tourPhotoId);

        tourPhotoService.changeRepresentativePhoto(tourPhotoId);

        return new ResponseEntity<>("대표사진 설정 완료", HttpStatus.OK);
    }
}
