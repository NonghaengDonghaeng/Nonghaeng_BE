package tour.nonghaeng.domain.photo.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.exception.error.PhotoErrorCode;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;
import tour.nonghaeng.domain.room.entity.Room;

@Component
@RequiredArgsConstructor
public class RoomPhotoValidator {

    private final RoomPhotoRepository roomPhotoRepository;


    public void ownerValidate(Member seller, Long photoId) {

        roomPhotoIdValidate(photoId);

        Photo photo = roomPhotoRepository.findPhotoById(photoId).get();

        if (!((Seller) seller).equals(photo.getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void numOfRepresentPhotoValidate(Room room) {

        if (!roomPhotoRepository.hasExactlyOneRepresentativePhoto(room)) {
            throw new PhotoException(PhotoErrorCode.WRONG_NUM_OF_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    public void deleteValidate(Room room, Long roomPhotoId) {

        if (roomPhotoRepository.findRepresentativePhotoId(room).get()
                .equals(roomPhotoId)) {

            throw new PhotoException(PhotoErrorCode.CANT_DELETE_REPRESENTATIVE_PHOTO_ERROR);
        }
    }

    private void roomPhotoIdValidate(Long roomPhotoId) {
        if (!roomPhotoRepository.existsById(roomPhotoId)) {
            throw new PhotoException(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
        }
    }
}
