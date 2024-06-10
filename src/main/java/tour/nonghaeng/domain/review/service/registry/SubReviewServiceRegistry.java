package tour.nonghaeng.domain.review.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.service.SubReviewService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubReviewServiceRegistry {

    private final Map<ReviewServiceType, SubReviewService> serviceMap;

    public SubReviewServiceRegistry(List<SubReviewService> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(SubReviewService::getType, Function.identity()));
    }

    public SubReviewService getService(String type) {
        return serviceMap.getOrDefault(ReviewServiceType.ofDtype(type),null);
    }
}
