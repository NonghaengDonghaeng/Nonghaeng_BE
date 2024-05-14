package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.exception.ReviewException;
import tour.nonghaeng.domain.review.repo.RoomReviewRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.room.valid.RoomValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomReviewService {

    private final RoomReviewRepository roomReviewRepository;

    private final RoomService roomService;

    private final RoomValidator roomValidator;



    public Long createRoomReview(Member user, Reservation reservation, CreateRoomReviewDto requestDto) {

        Room room = roomService.findById(requestDto.getRoomId());

        //TODO: validator

        return roomReviewRepository.save(requestDto.toEntity(user, reservation, room)).getId();
    }


    public Page<Review> findReviewPageByUser(Member user, Pageable pageable) {

        return roomReviewRepository.findReviewPageByUser((User) user, pageable);
    }


    public Page<Review> findReviewPageByRoomId(Long roomId, Pageable pageable) {

        roomValidator.roomIdValidate(roomId);

        return roomReviewRepository.findReviewPageByRoomId(roomId, pageable);
    }


    public Review findReviewById(Long reviewId) {
        return roomReviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> ReviewException.EXCEPTION);
    }
}
