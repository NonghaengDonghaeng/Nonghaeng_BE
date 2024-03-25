package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.RoomPhoto;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.validation.photo.RoomPhotoValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomPhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.ROOM;

    private final RoomPhotoRepository roomPhotoRepository;

    private final RoomService roomService;
    private final AmazonS3Service amazonS3Service;

    private final RoomPhotoValidator roomPhotoValidator;

    public Long upload(Seller seller, Long roomId, MultipartFile imageFile) {

        Room room = roomService.findById(roomId);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createRoomPhoto(room, imgUrl).getId();
    }

    public void delete(Seller seller, Long roomPhotoId) {

        roomPhotoValidator.ownerValidate(seller, roomPhotoId);

        amazonS3Service.deleteImage(PHOTO_TYPE,getUrlById(roomPhotoId));

        deleteRoomPhoto(roomPhotoId);
    }

    private void deleteRoomPhoto(Long roomPhotoId) {

        roomPhotoRepository.delete(findById(roomPhotoId));
    }

    private String getUrlById(Long roomPhotoId) {

        return findById(roomPhotoId).getImgUrl();
    }

    private RoomPhoto createRoomPhoto(Room room, String imgUrl) {

        return roomPhotoRepository.save(RoomPhoto.builder()
                .room(room)
                .imgUrl(imgUrl)
                .build());
    }

    public RoomPhoto findById(Long roomPhotoId) {
        return roomPhotoRepository.findById(roomPhotoId)
                    .orElseThrow(() -> PhotoException.EXCEPTION);
    }

}