package tour.nonghaeng.domain.like.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.exception.LikeException;
import tour.nonghaeng.domain.like.exception.error.LikeErrorCode;
import tour.nonghaeng.domain.like.repo.ExperienceLikeRepository;
import tour.nonghaeng.domain.like.repo.ReviewLikeRepository;
import tour.nonghaeng.domain.like.repo.RoomLikeRepository;
import tour.nonghaeng.domain.like.repo.TourLikeRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeValidator {

    private final ExperienceLikeRepository experienceLikeRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final RoomLikeRepository roomLikeRepository;
    private final TourLikeRepository tourLikeRepository;



    public void checkLikeType(String type) {

        List<String> types = List.of("room", "review", "tour", "experience");

        if(!types.contains(type)) {
            throw new LikeException(LikeErrorCode.WRONG_LIKE_TYPE);
        }
    }
}
