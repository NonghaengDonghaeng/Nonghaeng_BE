package tour.nonghaeng.domain.photo.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.global.infra.enums.photo.PhotoType;
import tour.nonghaeng.domain.photo.presentation.exception.PhotoException;
import tour.nonghaeng.domain.photo.data.repo.PhotoRepository;
import tour.nonghaeng.domain.photo.service.PhotoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PhotoServiceRegistry {

    private final Map<PhotoType, PhotoService> photoServiceMap;

    //TODO: 여기서 repository 사용하는게 맞는지 고민해보기
    //TODO: getService를 Optional 로 사용할지 말지 고민중
    private final PhotoRepository photoRepository;


    public PhotoServiceRegistry(List<PhotoService> photoServiceList, PhotoRepository photoRepository) {
        this.photoServiceMap = photoServiceList.stream()
                .collect(Collectors.toMap(PhotoService::getType, Function.identity()));
        this.photoRepository = photoRepository;
    }

    public Optional<PhotoService> getService(PhotoType photoType) {
        return Optional.ofNullable(photoServiceMap.get(photoType));
    }

    public Optional<PhotoService> getServiceByPhotoId(Long photoId) {

        String type = photoRepository.findPhotoType(photoId)
                .orElseThrow(PhotoException::new);

        return Optional.ofNullable(photoServiceMap.get(PhotoType.ofDtype(type)));
    }
}
