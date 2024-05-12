package tour.nonghaeng.domain.photo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.service.registry.PhotoServiceRegistry;
import tour.nonghaeng.global.auth.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class PhotoController {

    private final AuthService authService;

    private final PhotoServiceRegistry photoServiceRegistry;




    @PostMapping("/seller/uploads/{type}/{id}")
    public ResponseEntity<String> uploads(Authentication authentication,
                                          @RequestPart("images") List<MultipartFile> imageFiles,
                                          @PathVariable("type") String type,
                                          @PathVariable("id") Long id) {

        Seller seller = authService.toSellerEntity(authentication);

        photoServiceRegistry.getService(PhotoType.ofDtype(type)).
                ifPresent(photoService -> photoService.uploads(seller, id, imageFiles));

        return new ResponseEntity<>("images 업로드 완료.", HttpStatus.CREATED);
    }



    @DeleteMapping("/seller/{photoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("photoId") Long photoId) {

        Seller seller = authService.toSellerEntity(authentication);

        photoServiceRegistry.getServiceByPhotoId(photoId)
                .ifPresent(photoService -> photoService.delete(seller,photoId));

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }


    @GetMapping("/list/{type}/{id}")
    public ResponseEntity<List<PhotoInfoDto>> showAllImageList(@PathVariable("type") String type,
                                                               @PathVariable("id") Long id) {

        List<PhotoInfoDto> dtoList =
                photoServiceRegistry.getService(PhotoType.ofDtype(type))
                        .map(photoService -> photoService.getPhotoInfoListDto(id))
                        .orElse(null);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @GetMapping("/seller/representative/{type}/{id}")
    public ResponseEntity<String> changeRepresentativePhoto(Authentication authentication,
                                                            @PathVariable("type") String type,
                                                            @PathVariable("id") Long id) {

        Seller seller = authService.toSellerEntity(authentication);

        photoServiceRegistry.getService(PhotoType.ofDtype(type))
                .ifPresent(photoService -> photoService.changeRepresentativePhoto(seller, id));

        return new ResponseEntity<>("대표사진 설정 완료", HttpStatus.OK);
    }
}
