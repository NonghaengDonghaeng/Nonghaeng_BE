package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.data.RoomReview;
import tour.nonghaeng.domain.review.data.repo.RoomReviewRepository;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.presentation.exception.ReviewException;
import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.room.service.valid.RoomValidator;
import tour.nonghaeng.global.auth.AuthValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomReviewService implements ReviewService, CreateReviewService, FindUpCastedReviewService {

    private final RoomReviewRepository roomReviewRepository;

    private final RoomService roomService;
    private final ReservationService reservationService;

    private final RoomValidator roomValidator;
    private final AuthValidator authValidator;



    @Override
    public ReviewServiceType getType() {
        return ReviewServiceType.ROOM;
    }

    @Override
    public Long createReview(Member user, Long reservationId, CreateReviewDto requestDto) {

        Reservation reservation = reservationService.findById(reservationId);
        Room room = roomService.findById(requestDto.getId());

        //TODO: validator

        String formatTitle = "[" + room.getTour().getName() + "] " + requestDto.getTitle();
        RoomReview roomReview = RoomReview.builder()
                .user(authValidator.userValidate(user))
                .reservation(reservation)
                .room(room)
                .title(formatTitle)
                .content(requestDto.getContent())
                .build();


        return roomReviewRepository.save(roomReview).getId();
    }

    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable) {

        roomValidator.roomIdValidate(id);
        Page<Review> reviewPage = roomReviewRepository.findReviewPageByRoomId(id, pageable);

        return reviewPage.map(Review::toReviewSummaryDto);
    }

    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable) {

        Page<Review> reviewPage = roomReviewRepository.findReviewPageByUser(authValidator.userValidate(user), pageable);

        if(!reviewPage.hasContent()) {
            return Page.empty();
        }

        return reviewPage.map(Review::toReviewSummaryDto);
    }


    @Override
    public Review findReviewById(Long reviewId) {
        return roomReviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> ReviewException.EXCEPTION);
    }
}
