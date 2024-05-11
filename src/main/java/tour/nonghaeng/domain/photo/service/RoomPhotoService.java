package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.entity.RoomPhoto;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.imageServer.service.ImageService;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;
import tour.nonghaeng.domain.photo.valid.RoomPhotoValidator;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomPhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.ROOM;

    private final RoomPhotoRepository roomPhotoRepository;

    private final RoomService roomService;
    private final ImageService imageService;

    private final RoomPhotoValidator roomPhotoValidator;
    private final PhotoValidator photoValidator;




    public Long upload(Seller seller, Long roomId, MultipartFile imageFile) {

        Room room = roomService.findById(roomId);

        String imgUrl = imageService.uploadImage(PHOTO_TYPE, imageFile);

        return createRoomPhoto(seller, room, imgUrl).getId();
    }

    private RoomPhoto createRoomPhoto(Seller seller, Room room, String imgUrl) {

        RoomPhoto createdRoomPhoto = RoomPhoto.builder().room(room).seller(seller).imgUrl(imgUrl).build();

        if (!roomPhotoRepository.hasExactlyOneRepresentativePhoto(room)) {
            createdRoomPhoto.onRepresentative();
        }

        return roomPhotoRepository.save(createdRoomPhoto);
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

    private RoomPhoto findById(Long roomPhotoId) {
        return roomPhotoRepository.findById(roomPhotoId)
                    .orElseThrow(() -> PhotoException.EXCEPTION);
    }

    public Photo findPhotoById(Long roomPhotoId) {
        return roomPhotoRepository.findPhotoById(roomPhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }

}
