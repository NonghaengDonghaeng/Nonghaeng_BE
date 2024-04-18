package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.repo.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final RoomReviewService roomReviewService;
    private final ExperienceReviewService experienceReviewService;

    public Long createExpReview(User user, CreateExpReviewDto requestDto) {
        return experienceReviewService.createExperienceReview(user, requestDto);
    }

    public Long createRoomReview(User user, CreateRoomReviewDto requestDto) {
        return roomReviewService.createRoomReview(user, requestDto);
    }
}
