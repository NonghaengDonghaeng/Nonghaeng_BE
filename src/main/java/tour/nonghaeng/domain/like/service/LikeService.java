package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.like.valid.LikeValidator;
import tour.nonghaeng.domain.member.entity.User;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikeService {

    private final TourLikeService tourLikeService;
    private final ExperienceLikeService experienceLikeService;
    private final RoomLikeService roomLikeService;
    private final ReviewLikeService reviewLikeService;

    private final LikeValidator likeValidator;

    public boolean clickLike(User user, Long id, String type) {

        likeValidator.checkLikeType(type);

        if(type.equals("experience")) {

            return experienceLikeService.clickLike(user, id);
        }
        else if(type.equals("room")) {

            return roomLikeService.clickLike(user, id);
        }else if(type.equals("review")) {

            return reviewLikeService.clickLike(user, id);
        }

        return tourLikeService.clickLike(user, id);
    }


}
