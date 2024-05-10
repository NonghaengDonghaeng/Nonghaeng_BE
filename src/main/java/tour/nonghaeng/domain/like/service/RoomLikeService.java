package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.like.entity.RoomLike;
import tour.nonghaeng.domain.like.repo.RoomLikeRepository;
import tour.nonghaeng.domain.like.valid.RoomLikeValidator;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomLikeService {

    private final RoomLikeRepository roomLikeRepository;

    private final RoomService roomService;

    private final RoomLikeValidator roomLikeValidator;


    public void createLike(User user, Long roomId) {

        Room room = roomService.findById(roomId);

        roomLikeValidator.createLikeValidate(user.getId(), roomId);

        roomLikeRepository.save(RoomLike.builder().user(user).room(room).build());
    }
}
