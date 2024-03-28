package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.RoomPhoto;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.validation.photo.PhotoValidator;
import tour.nonghaeng.global.validation.photo.RoomPhotoValidator;

import java.util.List;

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
    private final PhotoValidator photoValidator;

    public Long upload(Long roomId, MultipartFile imageFile) {

        Room room = roomService.findById(roomId);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createRoomPhoto(room, imgUrl).getId();
    }

    public void delete(Long roomPhotoId) {

        amazonS3Service.deleteImage(PHOTO_TYPE,getUrlById(roomPhotoId));

        deleteRoomPhoto(roomPhotoId);
    }

    public List<PhotoInfoDto> getRoomPhotoInfoListDto(Long roomId) {

        List<Photo> photoList = roomPhotoRepository.findAllByRoom(roomService.findById(roomId));

        photoValidator.emptyPhotoListValidate(photoList);

        List<PhotoInfoDto> dto = PhotoInfoDto.toDtoList(photoList);

        return dto;
    }

    public PhotoInfoDto getRepresentRoomPhotoDto(Long roomId) {

        Room room = roomService.findById(roomId);

        roomPhotoValidator.numOfRepresentPhotoValidate(room);

        Long representId = roomPhotoRepository.findRepresentativePhotoId(room).get();

        return PhotoInfoDto.toDto(findById(representId));
    }

    public void changeRepresentativePhoto(Long roomPhotoId) {

        RoomPhoto roomPhoto = findById(roomPhotoId);
        Room room = roomPhoto.getRoom();

        roomPhotoValidator.numOfRepresentPhotoValidate(room);

        roomPhotoRepository.findRepresentativePhotoId(room)
                .ifPresent(id->{
                    RoomPhoto beforeRepresentativePhoto = findById(id);
                    beforeRepresentativePhoto.offRepresentative();
                    roomPhotoRepository.save(beforeRepresentativePhoto);
                });

        roomPhoto.onRepresentative();

        roomPhotoRepository.save(roomPhoto);
    }

    private void deleteRoomPhoto(Long roomPhotoId) {

        roomPhotoValidator.deleteValidate(findById(roomPhotoId).getRoom(),roomPhotoId);

        roomPhotoRepository.delete(findById(roomPhotoId));
    }

    private String getUrlById(Long roomPhotoId) {

        return findById(roomPhotoId).getImgUrl();
    }

    private RoomPhoto createRoomPhoto(Room room, String imgUrl) {

        RoomPhoto createdRoomPhoto = RoomPhoto.builder()
                .room(room)
                .imgUrl(imgUrl)
                .build();

        if (!roomPhotoRepository.hasExactlyOneRepresentativePhoto(room)) {
            createdRoomPhoto.onRepresentative();
        }

        return roomPhotoRepository.save(createdRoomPhoto);
    }

    public RoomPhoto findById(Long roomPhotoId) {
        return roomPhotoRepository.findById(roomPhotoId)
                    .orElseThrow(() -> PhotoException.EXCEPTION);
    }

}
