package tour.nonghaeng.domain.photo.service;

import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;

import java.util.List;

public interface PhotoService {

    PhotoType getType();

    void uploads(Seller seller, Long entityId, List<MultipartFile> imageFiles);

    List<PhotoInfoDto> getPhotoInfoListDto(Long entityId);

    void changeRepresentativePhoto(Seller seller, Long entityId);

    void delete(Seller seller,Long photoId);
}
