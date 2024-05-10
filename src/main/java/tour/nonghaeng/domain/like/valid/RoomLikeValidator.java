package tour.nonghaeng.domain.like.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.repo.RoomLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomLikeValidator {

    private final RoomLikeRepository roomLikeRepository;



}
