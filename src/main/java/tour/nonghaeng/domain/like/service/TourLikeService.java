package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.like.entity.TourLike;
import tour.nonghaeng.domain.like.repo.TourLikeRepository;
import tour.nonghaeng.domain.like.valid.TourLikeValidator;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourLikeService {

    private final TourLikeRepository tourLikeRepository;

    private final TourService tourService;

    private final TourLikeValidator tourLikeValidator;


    public void createLike(User user, Long tourId) {

        Tour tour = tourService.findById(tourId);

        tourLikeValidator.createLikeValidate(user.getId(), tourId);

        tourLikeRepository.save(TourLike.builder().user(user).tour(tour).build());
    }
}
