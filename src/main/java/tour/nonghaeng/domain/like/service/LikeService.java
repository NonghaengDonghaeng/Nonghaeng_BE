package tour.nonghaeng.domain.like.service;

import tour.nonghaeng.domain.etc.enums.like.LikeType;
import tour.nonghaeng.domain.member.entity.Member;

public interface LikeService {

    LikeType getType();

    boolean clickLike(Member user, Long id);
}
