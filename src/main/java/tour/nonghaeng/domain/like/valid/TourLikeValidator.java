package tour.nonghaeng.domain.like.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.exception.LikeException;
import tour.nonghaeng.domain.like.exception.error.LikeErrorCode;
import tour.nonghaeng.domain.like.repo.TourLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class TourLikeValidator {

    private final TourLikeRepository tourLikeRepository;

    public void createLikeValidate(Long userId, Long tourId) {
        if (tourLikeRepository.countByTourIdAndUserId(userId, tourId) != 0) {
            throw new LikeException(LikeErrorCode.ALREADY_EXISTED_LIKE_ERROR);
        }
    }
}
