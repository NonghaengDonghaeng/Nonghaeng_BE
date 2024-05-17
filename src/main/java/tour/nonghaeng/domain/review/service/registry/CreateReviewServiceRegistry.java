package tour.nonghaeng.domain.review.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.review.ReviewServiceType;
import tour.nonghaeng.domain.review.service.interfac.CreateReviewService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CreateReviewServiceRegistry {

    private final Map<ReviewServiceType, CreateReviewService> createReviewServiceMap;

    public CreateReviewServiceRegistry(List<CreateReviewService> createReviewServiceList) {
        this.createReviewServiceMap = createReviewServiceList.stream()
                .collect(Collectors.toMap(CreateReviewService::getType, Function.identity()));
    }

    public CreateReviewService getService(String type) {
        return createReviewServiceMap.get(ReviewServiceType.ofDtype(type));
    }
}
