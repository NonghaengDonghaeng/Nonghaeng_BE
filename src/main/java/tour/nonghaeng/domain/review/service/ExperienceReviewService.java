package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.entity.User;
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
}
