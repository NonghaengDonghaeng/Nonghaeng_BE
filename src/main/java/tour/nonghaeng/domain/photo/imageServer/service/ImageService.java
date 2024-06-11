package tour.nonghaeng.domain.photo.imageServer.service;

import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.global.infra.enums.photo.PhotoType;

public interface ImageService {

    String uploadImage(PhotoType photoType, MultipartFile image);

    void deleteImage(PhotoType photoType, String imgUrl);
}
