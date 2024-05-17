package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.experience.valid.ExperienceValidator;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.entity.ExperienceReview;
import tour.nonghaeng.domain.review.entity.Review;
import tour.nonghaeng.domain.review.exception.ReviewException;
import tour.nonghaeng.domain.review.repo.ExperienceReviewRepository;
import tour.nonghaeng.domain.review.service.interfac.CreateReviewService;
import tour.nonghaeng.domain.review.service.interfac.FindUpCastedReviewService;
import tour.nonghaeng.domain.review.service.interfac.ReviewService;
import tour.nonghaeng.global.auth.valid.AuthValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceReviewService implements ReviewService, CreateReviewService, FindUpCastedReviewService {

    private final ExperienceReviewRepository experienceReviewRepository;

    private final ExperienceService experienceService;
    private final ReservationService reservationService;

    private final ExperienceValidator experienceValidator;
    private final AuthValidator authValidator;




    @Override
    public ReviewServiceType getType() {
        return ReviewServiceType.EXPERIENCE;
    }

    @Override
    public Long createReview(Member user,Long reservationId, CreateReviewDto requestDto) {

        Reservation reservation = reservationService.findById(reservationId);
        Experience experience = experienceService.findById(requestDto.getId());

        //TODO: validator

        String formatTitle = "[" + experience.getTour().getName() + "] " + requestDto.getTitle();

        ExperienceReview experienceReview = ExperienceReview.builder()
                .user(authValidator.userValidate(user))
                .reservation(reservation)
                .experience(experience)
                .title(formatTitle)
                .content(requestDto.getContent())
                .build();

        return experienceReviewRepository.save(experienceReview).getId();
    }

    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageById(Long id, Pageable pageable) {

        experienceValidator.expIdValidate(id);

        Page<Review> reviewPage = experienceReviewRepository.findReviewPageByExpId(id, pageable);

        return reviewPage.map(Review::toReviewSummaryDto);
    }

    @Override
    public Page<? extends ReviewSummaryDto> getReviewSummaryDtoPageByUser(Member user, Pageable pageable) {

        Page<Review> reviewPage = experienceReviewRepository.findReviewPageByUser(authValidator.userValidate(user), pageable);

        if(!reviewPage.hasContent()) {
            return Page.empty();
        }

        return reviewPage.map(Review::toReviewSummaryDto);
    }



    public Review findReviewById(Long reviewId) {
        return experienceReviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> ReviewException.EXCEPTION);
    }


}
