package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.etc.like.LikeType;
import tour.nonghaeng.domain.like.entity.TourLike;
import tour.nonghaeng.domain.like.repo.TourLikeRepository;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourLikeService implements LikeService {

    private static final LikeType LIKE_TYPE = LikeType.TOUR;

    private final TourLikeRepository tourLikeRepository;

    private final TourService tourService;


    @Override
    public LikeType getType() {
        return LIKE_TYPE;
    }

    @Override
    public boolean clickLike(Member user, Long tourId) {

        Tour tour = tourService.findById(tourId);

        Optional<TourLike> maybeTourLike = tourLikeRepository.findByUserAndTour((User) user, tour);

        return maybeTourLike.map(this::offLike).orElseGet(() -> onLike(user, tour));
    }

    private boolean onLike(Member user, Tour tour) {

        tourLikeRepository.save(TourLike.builder().user((User) user).tour(tour).build());
        return true;
    }

    private boolean offLike(TourLike tourLike) {

        tourLikeRepository.delete(tourLike);
        return false;
    }
}
