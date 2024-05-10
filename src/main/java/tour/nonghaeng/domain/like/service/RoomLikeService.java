package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.like.entity.RoomLike;
import tour.nonghaeng.domain.like.repo.RoomLikeRepository;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomLikeService {

    private final RoomLikeRepository roomLikeRepository;

    private final RoomService roomService;



    public boolean clickLike(User user, Long roomId) {

        Room room = roomService.findById(roomId);

        Optional<RoomLike> maybeRoomLike = roomLikeRepository.findByUserAndRoom(user, room);

        return maybeRoomLike.map(this::offLike).orElseGet(() -> onLike(user, room));
    }

    private boolean onLike(User user, Room room) {

        roomLikeRepository.save(RoomLike.builder().user(user).room(room).build());
        return true;
    }

    private boolean offLike(RoomLike roomLike) {

        roomLikeRepository.delete(roomLike);
        return false;
    }
}
