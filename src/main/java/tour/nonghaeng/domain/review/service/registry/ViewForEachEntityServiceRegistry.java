package tour.nonghaeng.domain.review.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.service.ViewForEachEntityService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewForEachEntityServiceRegistry {

    private final Map<ReviewServiceType, ViewForEachEntityService> reviewServiceMap;

    public ViewForEachEntityServiceRegistry(List<ViewForEachEntityService> viewForEachEntityServiceList) {
        this.reviewServiceMap = viewForEachEntityServiceList.stream()
                .collect(Collectors.toMap(ViewForEachEntityService::getType, Function.identity()));
    }

    public ViewForEachEntityService getService(String type) {
        if(type.equals("room")||type.equals("experience")){
            return reviewServiceMap.get(ReviewServiceType.ofDtype(type));
        }
        return reviewServiceMap.get(ReviewServiceType.TOUR_AND_ALL);
    }
}
