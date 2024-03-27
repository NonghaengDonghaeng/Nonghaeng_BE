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
import tour.nonghaeng.domain.photo.service.ExperiencePhotoService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.experience.ExperienceValidator;
import tour.nonghaeng.global.validation.photo.ExperiencePhotoValidator;

import java.util.List;

@RestController
@RequestMapping("/image/experiences")
@RequiredArgsConstructor
@Slf4j
public class ExperiencePhotoController {

    private final ExperiencePhotoService experiencePhotoService;
    private final AuthService authService;

    private final ExperiencePhotoValidator experiencePhotoValidator;
    private final ExperienceValidator experienceValidator;

    @PostMapping("/seller/upload/{experienceId}")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile imageFile,
                                         @PathVariable("experienceId") Long experienceId) {

        Seller seller = authService.toSellerEntity(authentication);

        experienceValidator.ownerValidate(seller, experienceId);

        Long uploadId = experiencePhotoService.upload(experienceId, imageFile);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
    }

    @DeleteMapping("/seller/{experiencePhotoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("experiencePhotoId") Long experiencePhotoId) {

        Seller seller = authService.toSellerEntity(authentication);

        experiencePhotoValidator.ownerValidate(seller, experiencePhotoId);

        experiencePhotoService.delete(experiencePhotoId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }

    @GetMapping("/list/{experienceId}")
    public ResponseEntity<List<PhotoInfoDto>> showAllImageList(@PathVariable("experienceId") Long experienceId) {

        List<PhotoInfoDto> dtoList = experiencePhotoService.getExpPhotoInfoListDto(experienceId);

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/seller/representative/{expPhotoId}")
    public ResponseEntity<String> changeRepresentativePhoto(Authentication authentication,
                                                            @PathVariable("expPhotoId") Long expPhotoId) {

        experiencePhotoValidator.ownerValidate(authService.toSellerEntity(authentication), expPhotoId);

        experiencePhotoService.changeRepresentativePhoto(expPhotoId);

        return new ResponseEntity<>("대표사진 설정 완료", HttpStatus.OK);
    }
}
