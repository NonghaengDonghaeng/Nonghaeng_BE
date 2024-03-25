package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.RoomPhoto;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.exception.code.PhotoErrorCode;

@Component
@RequiredArgsConstructor
public class RoomPhotoValidator {

    private final RoomPhotoRepository roomPhotoRepository;

    public void ownerValidate(Seller seller, Long roomPhotoId) {

        roomPhotoIdValidate(roomPhotoId);

        RoomPhoto roomPhoto = roomPhotoRepository.findById(roomPhotoId).get();

        if (!seller.equals(roomPhoto.getRoom().getSeller())) {
            throw new PhotoException(PhotoErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

    }

    private void roomPhotoIdValidate(Long roomPhotoId) {
        if (!roomPhotoRepository.existsById(roomPhotoId)) {
            throw new PhotoException(PhotoErrorCode.DEFAULT_PHOTO_ERROR);
        }
    }
}
