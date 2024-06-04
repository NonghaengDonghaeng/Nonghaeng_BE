package tour.nonghaeng.domain.review.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.service.CrudReviewService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CrudReviewServiceRegistry {

    private final Map<ReviewServiceType, CrudReviewService> serviceMap;

    public CrudReviewServiceRegistry(List<CrudReviewService> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(CrudReviewService::getType, Function.identity()));
    }

    public CrudReviewService getService(String type) {
        return serviceMap.get(ReviewServiceType.ofDtype(type));
    }
}
