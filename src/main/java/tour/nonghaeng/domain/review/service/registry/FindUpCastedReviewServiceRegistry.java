package tour.nonghaeng.domain.review.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.service.interfac.FindUpCastedReviewService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FindUpCastedReviewServiceRegistry {

    private final Map<ReviewServiceType, FindUpCastedReviewService> serviceMap;

    public FindUpCastedReviewServiceRegistry(List<FindUpCastedReviewService> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(FindUpCastedReviewService::getType, Function.identity()));
    }

    public FindUpCastedReviewService getService(String type) {
        return serviceMap.get(ReviewServiceType.ofDtype(type));
    }
}
