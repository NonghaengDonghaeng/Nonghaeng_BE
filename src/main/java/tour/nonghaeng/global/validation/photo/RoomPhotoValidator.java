package tour.nonghaeng.global.validation.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.photo.repo.RoomPhotoRepository;

@Component
@RequiredArgsConstructor
public class RoomPhotoValidator {

    private final RoomPhotoRepository roomPhotoRepository;
}
