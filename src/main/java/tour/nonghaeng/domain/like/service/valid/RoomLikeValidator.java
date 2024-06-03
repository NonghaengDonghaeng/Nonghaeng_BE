package tour.nonghaeng.domain.like.service.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.data.repo.RoomLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomLikeValidator {

    private final RoomLikeRepository roomLikeRepository;



}
