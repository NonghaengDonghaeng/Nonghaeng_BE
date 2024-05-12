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
import tour.nonghaeng.domain.photo.valid.RoomPhotoValidator;
import tour.nonghaeng.global.auth.auth.service.AuthService;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;
import tour.nonghaeng.domain.room.valid.RoomValidator;

import java.util.List;

@RestController
@RequestMapping("/image/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomPhotoController {

    private final RoomPhotoService roomPhotoService;
    private final AuthService authService;

    private final RoomValidator roomValidator;
    private final RoomPhotoValidator roomPhotoValidator;
    private final PhotoValidator photoValidator;




    @PostMapping("/seller/uploads/{roomId}")
    public ResponseEntity<String> uploads(Authentication authentication,
                                         @RequestPart("images") List<MultipartFile> imageFiles,
                                         @PathVariable("roomId") Long roomId) {

        Seller seller = authService.toSellerEntity(authentication);

        roomValidator.ownerValidate(seller,roomId);

        roomPhotoService.uploads(seller, roomId, imageFiles);

        return new ResponseEntity<>("images 업로드 완료.", HttpStatus.CREATED);
    }



    @PostMapping("/seller/upload/{roomId}")
    public ResponseEntity<String> upload(Authentication authentication,
                                         @RequestPart("image") MultipartFile imageFile,
                                         @PathVariable("roomId") Long roomId) {

        Seller seller = authService.toSellerEntity(authentication);

        roomValidator.ownerValidate(seller,roomId);

        Long uploadId = roomPhotoService.upload(seller, roomId, imageFile);

        return new ResponseEntity<>("업로드 완료. id:" + String.valueOf(uploadId), HttpStatus.CREATED);
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
