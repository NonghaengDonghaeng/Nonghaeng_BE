package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.repo.RoomReviewRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomReviewService {

    private final RoomReviewRepository roomReviewRepository;

    private final RoomService roomService;
    private final UserService userService;

    public Long createRoomReview(User user, CreateRoomReviewDto requestDto) {

        Room room = roomService.findById(requestDto.getRoomId());

        //TODO: validator

        return roomReviewRepository.save(requestDto.toEntity(user, room)).getId();
    }

    public List<Review> findReviewListByUser(User user) {
        return roomReviewRepository.findReviewByUser(user);
    }

    private List<Review> findReviewListByRoom(Room room) {
        return roomReviewRepository.findReviewByRoom(room);
    }

    public List<ReviewSummaryDto> findReviewListByRoomId(Long roomId) {

        Room room = roomService.findById(roomId);
        List<Review> roomReviewList = findReviewListByRoom(room);

        return roomReviewList.stream().map(Review::toReviewSummaryDto).toList();
    }
}
