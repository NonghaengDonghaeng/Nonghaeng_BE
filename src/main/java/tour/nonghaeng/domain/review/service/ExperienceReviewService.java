package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.repo.ExperienceReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceReviewService {

    private final ExperienceReviewRepository experienceReviewRepository;

    private final ExperienceService experienceService;

    public Long createExperienceReview(User user, CreateExpReviewDto requestDto) {

        Experience experience = experienceService.findById(requestDto.getExpId());

        //TODO: validator

        return experienceReviewRepository.save(requestDto.toEntity(user, experience)).getId();
    }

    public List<Review> findReviewListByUser(User user) {
        return experienceReviewRepository.findReviewByUser(user);
    }

    public List<Review> findReviewListByExperience(Experience experience) {
        return experienceReviewRepository.findReviewByExperience(experience);
    }

    public List<ReviewSummaryDto> findReviewListByExperienceId(Long experienceId) {

        Experience experience = experienceService.findById(experienceId);
        List<Review> roomReviewList = findReviewListByExperience(experience);

        return roomReviewList.stream().map(Review::toReviewSummaryDto).toList();
    }

    public Page<Review> findReviewPageByUser(User user, Pageable pageable) {
        return experienceReviewRepository.findReviewPageByUser(user, pageable);
    }

    public Review findReviewById(Long reviewId) {
        return experienceReviewRepository.findById(reviewId).orElse(null);
    }
}
