package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.global.infra.enums.like.LikeType;
import tour.nonghaeng.domain.like.data.RoomLike;
import tour.nonghaeng.domain.like.data.repo.RoomLikeRepository;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.global.auth.AuthValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomLikeService implements LikeService {

    private static final LikeType LIKE_TYPE = LikeType.ROOM;

    private final RoomLikeRepository roomLikeRepository;

    private final RoomService roomService;

    private final AuthValidator authValidator;


    @Override
    public LikeType getType() {
        return LIKE_TYPE;
    }

    @Override
    public boolean clickLike(Member user, Long roomId) {

        Room room = roomService.findById(roomId);

        Optional<RoomLike> maybeRoomLike = roomLikeRepository.findByUserAndRoom(authValidator.userValidate(user), room);

        return maybeRoomLike.map(this::offLike).orElseGet(() -> onLike(user, room));
    }

    private boolean onLike(Member user, Room room) {

        roomLikeRepository.save(RoomLike.builder()
                .user(authValidator.userValidate(user))
                .room(room)
                .build()
        );
        return true;
    }

    private boolean offLike(RoomLike roomLike) {

        roomLikeRepository.delete(roomLike);
        return false;
    }
}
