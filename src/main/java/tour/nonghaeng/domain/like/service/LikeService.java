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

    public void createLike(User user, Long id, String type) {

        likeValidator.checkLikeType(type);

        if(type.equals("experience")) {

            experienceLikeService.createLike(user, id);

            return;
        }
        else if(type.equals("room")) {

            roomLikeService.createLike(user, id);

            return;
        }else if(type.equals("review")) {

            reviewLikeService.createLike(user, id);

            return;
        }

        tourLikeService.createLike(user, id);

        return;
    }


}
