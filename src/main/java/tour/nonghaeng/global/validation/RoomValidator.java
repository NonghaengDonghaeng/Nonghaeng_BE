package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomRepository roomRepository;

    public void pageValidate(Page<Tour> tourPage) {
        if (tourPage.isEmpty()) {
            throw new RoomException(RoomErrorCode.NO_TOUR_ROOM_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }
}
