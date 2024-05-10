package tour.nonghaeng.domain.like.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.exception.LikeException;
import tour.nonghaeng.domain.like.exception.error.LikeErrorCode;
import tour.nonghaeng.domain.like.repo.ExperienceLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExperienceLikeValidator {

    private final ExperienceLikeRepository experienceLikeRepository;

    public void createLikeValidate(Long userId, Long experienceId) {
        if (experienceLikeRepository.countByExperienceIdAndUserId(userId, experienceId) != 0) {
            throw new LikeException(LikeErrorCode.ALREADY_EXISTED_LIKE_ERROR);
        }
    }
}
