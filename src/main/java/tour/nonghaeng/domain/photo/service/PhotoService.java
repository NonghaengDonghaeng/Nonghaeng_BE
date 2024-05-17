package tour.nonghaeng.domain.photo.service;

import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.enums.photo.PhotoType;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;

import java.util.List;

public interface PhotoService {

    PhotoType getType();

    void uploads(Member member, Long entityId, List<MultipartFile> imageFiles);

    List<PhotoInfoDto> getPhotoInfoListDto(Long entityId);

    void changeRepresentativePhoto(Member member, Long entityId);

    void delete(Member member,Long photoId);
}
