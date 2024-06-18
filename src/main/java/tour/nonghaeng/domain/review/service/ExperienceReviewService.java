package tour.nonghaeng.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.experience.service.valid.ExperienceValidator;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.reservation.service.ReservationServiceImpl;
import tour.nonghaeng.domain.review.data.ExperienceReview;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.domain.review.data.repo.ExperienceReviewRepository;
import tour.nonghaeng.domain.review.dto.CreateReviewDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.presentation.exception.ReviewException;
import tour.nonghaeng.global.auth.AuthValidator;
import tour.nonghaeng.global.infra.enums.review.ReviewServiceType;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceReviewService implements SubReviewService {

    private final ExperienceReviewRepository experienceReviewRepository;

    private final ExperienceService experienceService;
    private final ReservationService reservationService;

    private final ExperienceValidator experienceValidator;
    private final AuthValidator authValidator;
    private final ReservationServiceImpl reservationServiceImpl;


    //ViewForEachEntityService
    @Override
    public ReviewServiceType getType() {
        return ReviewServiceType.EXPERIENCE;
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



    //CrudReviewService
    @Override
    public Review findById(Long reviewId) {
        return experienceReviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> ReviewException.EXCEPTION);
    }


    @Override
    public Review create(Member user, CreateReviewDto createDto) {

        Reservation reservation = reservationService.findById(createDto.getReservationId());
        Experience experience = experienceService.findById(reservation.getEntityId());

        //TODO: validator

        String formatTitle = "[" + experience.getTour().getName() + "] " + createDto.getTitle();

        ExperienceReview experienceReview = ExperienceReview.builder()
                .user(authValidator.userValidate(user))
                .reservation(reservation)
                .experience(experience)
                .title(formatTitle)
                .content(createDto.getContent())
                .build();

        reservationService.setWrittenReview(reservation);


        return experienceReviewRepository.save(experienceReview);
    }
}
