package tour.nonghaeng.domain.review.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.review.ReviewServiceType;
import tour.nonghaeng.domain.review.service.interfac.ReviewService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReviewServiceRegistry {

    private final Map<ReviewServiceType, ReviewService> reviewServiceMap;

    public ReviewServiceRegistry(List<ReviewService> reviewServiceList) {
        this.reviewServiceMap = reviewServiceList.stream()
                .collect(Collectors.toMap(ReviewService::getType, Function.identity()));
    }

    public ReviewService getService(String type) {
        if(type.equals("room")||type.equals("experience")){
            return reviewServiceMap.get(ReviewServiceType.ofDtype(type));
        }
        return reviewServiceMap.get(ReviewServiceType.TOUR_AND_ALL);
    }
}
