package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.dto.room.CreateRoomReviewDto;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.repo.ReviewRepository;
import tour.nonghaeng.domain.room.service.RoomService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final RoomReviewService roomReviewService;
    private final ExperienceReviewService experienceReviewService;
    private final RoomService roomService;

    public Long createExpReview(User user, CreateExpReviewDto requestDto) {
        return experienceReviewService.createExperienceReview(user, requestDto);
    }

    public Long createRoomReview(User user, CreateRoomReviewDto requestDto) {
        return roomReviewService.createRoomReview(user, requestDto);
    }

    public List<ReviewSummaryDto> getReviewSummaryDtoListByUser(User user) {

        List<Long> reviewIdList = reviewRepository.findReviewIdListByUser(user);

        List<Review> list = reviewIdList.stream().map(this::getUpCastedReview).toList();

        return list.stream().map(Review::toReviewSummaryDto).toList();
    }

    private Review getUpCastedReview(Long reviewId) {

        String dtype = reviewRepository.findReviewTypeById(reviewId);

        if (dtype.equals("room")) {

            return roomReviewService.findReviewById(reviewId);
        }

        return experienceReviewService.findReviewById(reviewId);
    }

    public List<ReviewSummaryDto> findReviewList(User user,Long id, String type){

        if(type.equals("room")){
            return roomReviewService.findReviewListByRoomId(id);
        }

        return experienceReviewService.findReviewListByExperienceId(id);
    }

    private Page<Review> findReviewPageByUserAndType(User user,Pageable pageable,String type){

        if(type.equals("room")){
            return roomReviewService.findReviewPageByUser(user, pageable);
        }
        else if(type.equals("experience")){
            return experienceReviewService.findReviewPageByUser(user, pageable);
        }
        return findReviewPageByUser(user,pageable);
    }

    private Page<Review> findReviewPageByUser(User user, Pageable pageable) {
        Page<Review> reviewPageByUser = reviewRepository.findReviewPageByUser(user, pageable);

        List<Review> list = reviewPageByUser.getContent().stream().map(this::downCastingReview).toList();

        return new PageImpl<>(list, pageable, reviewPageByUser.getTotalElements());
    }

    private Review downCastingReview(Review review) {
        if (reviewRepository.findReviewTypeById(review.getId()).equals("room")) {
            return roomReviewService.findReviewById(review.getId());
        }
        else{
            return experienceReviewService.findReviewById(review.getId());
        }
    }
}
