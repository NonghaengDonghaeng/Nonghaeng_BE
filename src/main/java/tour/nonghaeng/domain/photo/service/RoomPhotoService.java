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
import tour.nonghaeng.domain.room.valid.RoomValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomPhotoService implements PhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.ROOM;

    private final RoomPhotoRepository roomPhotoRepository;

    private final RoomService roomService;
    private final ImageService imageService;

    private final RoomPhotoValidator roomPhotoValidator;
    private final RoomValidator roomValidator;
    private final PhotoValidator photoValidator;


    @Override
    public PhotoType getType() {
        return PHOTO_TYPE;
    }

    @Override
    public void uploads(Seller seller, Long roomId, List<MultipartFile> imageFiles) {

        roomValidator.ownerValidate(seller,roomId);

        Room room = roomService.findById(roomId);

        for (MultipartFile imageFile : imageFiles) {

            String imgUrl = imageService.uploadImage(PHOTO_TYPE, imageFile);

            createRoomPhoto(seller, room, imgUrl);
        }
    }

    private void createRoomPhoto(Seller seller, Room room, String imgUrl) {

        RoomPhoto createdRoomPhoto = RoomPhoto.builder().room(room).seller(seller).imgUrl(imgUrl).build();

        if (!roomPhotoRepository.hasExactlyOneRepresentativePhoto(room)) {
            createdRoomPhoto.onRepresentative();
        }

        roomPhotoRepository.save(createdRoomPhoto);
    }


    @Override
    public List<PhotoInfoDto> getPhotoInfoListDto(Long roomId) {

        List<Photo> photoList = roomPhotoRepository.findAllByRoom(roomService.findById(roomId));

        photoValidator.emptyPhotoListValidate(photoList);

        return PhotoInfoDto.toDtoList(photoList);
    }


    @Override
    public void changeRepresentativePhoto(Seller seller,Long roomPhotoId) {


        roomPhotoValidator.ownerValidate(seller, roomPhotoId);

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

    @Override
    public void delete(Seller seller, Long photoId) {

        photoValidator.deletePhotoValidate(photoId);
        roomPhotoValidator.ownerValidate(seller, photoId);

        RoomPhoto roomPhoto = findById(photoId);

        imageService.deleteImage(PHOTO_TYPE, roomPhoto.getImgUrl());

        roomPhotoRepository.delete(roomPhoto);
    }

    private RoomPhoto findById(Long roomPhotoId) {
        return roomPhotoRepository.findById(roomPhotoId)
                    .orElseThrow(() -> PhotoException.EXCEPTION);
    }

    private Photo findPhotoById(Long roomPhotoId) {
        return roomPhotoRepository.findPhotoById(roomPhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }
}
