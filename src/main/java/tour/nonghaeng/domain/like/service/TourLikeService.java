package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.like.entity.TourLike;
import tour.nonghaeng.domain.like.repo.TourLikeRepository;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourLikeService {

    private final TourLikeRepository tourLikeRepository;

    private final TourService tourService;



    public boolean clickLike(User user, Long tourId) {

        Tour tour = tourService.findById(tourId);

        Optional<TourLike> maybeTourLike = tourLikeRepository.findByUserAndTour(user, tour);

        return maybeTourLike.map(this::offLike).orElseGet(() -> onLike(user, tour));
    }

    private boolean onLike(User user, Tour tour) {

        tourLikeRepository.save(TourLike.builder().user(user).tour(tour).build());
        return true;
    }

    private boolean offLike(TourLike tourLike) {

        tourLikeRepository.delete(tourLike);
        return false;
    }
}
