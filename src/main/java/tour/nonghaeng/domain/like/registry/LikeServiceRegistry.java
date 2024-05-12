package tour.nonghaeng.domain.like.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.like.LikeType;
import tour.nonghaeng.domain.like.service.LikeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LikeServiceRegistry {

    private final Map<LikeType, LikeService> likeServiceMap;

    public LikeServiceRegistry(List<LikeService> likeServiceList) {
        this.likeServiceMap = likeServiceList.stream()
                .collect(Collectors.toMap(LikeService::getType, Function.identity()));
    }

    public Optional<LikeService> getService(LikeType likeType) {
        return Optional.ofNullable(likeServiceMap.get(likeType));
    }
}
