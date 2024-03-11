package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.room.repo.RoomCloseDateRepository;
import tour.nonghaeng.global.validation.RoomCloseDateValidator;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomCloseDateService {

    private final RoomCloseDateRepository roomCloseDateRepository;

    private final RoomCloseDateValidator roomCloseDateValidator;
}
