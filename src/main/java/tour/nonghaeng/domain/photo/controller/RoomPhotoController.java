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
import tour.nonghaeng.domain.photo.service.RoomPhotoService;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.photo.RoomPhotoValidator;

import java.util.List;

@RestController
@RequestMapping("/image/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomPhotoController {

    private final RoomPhotoService roomPhotoService;
    private final AuthService authService;

    private final RoomPhotoValidator roomPhotoValidator;

    @PostMapping("/seller/upload/{roomId}")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile imageFile,
                                         @PathVariable("roomId") Long roomId) {

        Seller seller = authService.toSellerEntity(authentication);

        Long uploadId = roomPhotoService.upload(seller, roomId, imageFile);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
    }

    @DeleteMapping("/seller/{roomPhotoId}")
    public ResponseEntity<String> delete(Authentication authentication,
                                         @PathVariable("roomPhotoId") Long roomPhotoId) {

        Seller seller = authService.toSellerEntity(authentication);

        roomPhotoService.delete(seller, roomPhotoId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }

    @GetMapping("/list/{roomId}")
    public ResponseEntity<List<PhotoInfoDto>> showAllImageList(@PathVariable("roomId") Long roomId) {

        List<PhotoInfoDto> roomPhotoInfoListDto = roomPhotoService.getRoomPhotoInfoListDto(roomId);

        return new ResponseEntity<>(roomPhotoInfoListDto, HttpStatus.OK);
    }

    @GetMapping("/seller/representative/{roomPhotoId}")
    public ResponseEntity<String> changeRepresentativePhoto(Authentication authentication,
                                                            @PathVariable("roomPhotoId") Long roomPhotoId) {

        roomPhotoValidator.ownerValidate(authService.toSellerEntity(authentication), roomPhotoId);

        roomPhotoService.changeRepresentativePhoto(roomPhotoId);

        return new ResponseEntity<>("대표사진 설정 완료", HttpStatus.OK);
    }
}
