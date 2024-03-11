package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.room.repo.RoomCloseDateRepository;

@Component
@RequiredArgsConstructor
public class RoomCloseDateValidator {

    private final RoomCloseDateRepository roomCloseDateRepository;

}
