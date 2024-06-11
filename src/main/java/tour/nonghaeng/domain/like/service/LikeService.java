package tour.nonghaeng.domain.like.service;

import tour.nonghaeng.global.infra.enums.like.LikeType;
import tour.nonghaeng.domain.member.data.Member;

public interface LikeService {

    LikeType getType();

    boolean clickLike(Member user, Long id);
}
