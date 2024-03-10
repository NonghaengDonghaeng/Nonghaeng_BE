package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.room.repo.RoomRepository;

@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomRepository roomRepository;
}
