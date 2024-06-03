package tour.nonghaeng.domain.like.service.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.presentation.exception.LikeException;
import tour.nonghaeng.domain.like.presentation.exception.error.LikeErrorCode;
import tour.nonghaeng.domain.like.data.repo.ExperienceLikeRepository;
import tour.nonghaeng.domain.like.data.repo.ReviewLikeRepository;
import tour.nonghaeng.domain.like.data.repo.RoomLikeRepository;
import tour.nonghaeng.domain.like.data.repo.TourLikeRepository;

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
