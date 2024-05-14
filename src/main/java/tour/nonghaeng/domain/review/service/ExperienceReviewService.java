package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.experience.valid.ExperienceValidator;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.review.dto.exp.CreateExpReviewDto;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.exception.ReviewException;
import tour.nonghaeng.domain.review.repo.ExperienceReviewRepository;
import tour.nonghaeng.global.auth.valid.AuthValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceReviewService {

    private final ExperienceReviewRepository experienceReviewRepository;

    private final ExperienceService experienceService;

    private final ExperienceValidator experienceValidator;
    private final AuthValidator authValidator;



    public Long createExperienceReview(Member user, Reservation reservation, CreateExpReviewDto requestDto) {

        Experience experience = experienceService.findById(requestDto.getExpId());

        //TODO: validator

        return experienceReviewRepository.save(requestDto.toEntity(authValidator.userValidate(user), reservation, experience)).getId();
    }


    public Page<Review> findReviewPageByUser(Member user, Pageable pageable) {

        return experienceReviewRepository.findReviewPageByUser(authValidator.userValidate(user), pageable);
    }


    public Page<Review> findReviewPageByExpId(Long experienceId, Pageable pageable) {

        experienceValidator.expIdValidate(experienceId);

        return experienceReviewRepository.findReviewPageByExpId(experienceId,pageable);
    }



    public Review findReviewById(Long reviewId) {
        return experienceReviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> ReviewException.EXCEPTION);
    }
}
