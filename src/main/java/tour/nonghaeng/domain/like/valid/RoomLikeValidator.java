package tour.nonghaeng.domain.like.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.exception.LikeException;
import tour.nonghaeng.domain.like.exception.error.LikeErrorCode;
import tour.nonghaeng.domain.like.repo.RoomLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomLikeValidator {

    private final RoomLikeRepository roomLikeRepository;

    public void createLikeValidate(Long userId, Long roomId) {
        if (roomLikeRepository.countByRoomIdAndUserId(userId, roomId) != 0) {
            throw new LikeException(LikeErrorCode.ALREADY_EXISTED_LIKE_ERROR);
        }
    }
}
