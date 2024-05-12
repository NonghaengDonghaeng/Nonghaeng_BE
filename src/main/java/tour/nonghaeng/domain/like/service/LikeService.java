package tour.nonghaeng.domain.like.service;

import tour.nonghaeng.domain.etc.like.LikeType;
import tour.nonghaeng.domain.member.entity.User;

public interface LikeService {

    LikeType getType();

    boolean clickLike(User user, Long id);
}
