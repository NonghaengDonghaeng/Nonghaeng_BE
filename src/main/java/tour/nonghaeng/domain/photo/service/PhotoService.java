package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.imageServer.service.ImageService;
import tour.nonghaeng.domain.photo.repo.PhotoRepository;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PhotoService {

    private final PhotoRepository photoRepository;

    private final ImageService imageService;

    private final PhotoValidator photoValidator;




    public void delete(Long photoId) {

        PhotoType photoType = findPhotoTypeById(photoId);

        imageService.deleteImage(photoType, getUrlById(photoId));

        deletePhoto(photoId);
    }

    private PhotoType findPhotoTypeById(Long photoId) {

        String dtype = photoRepository.findPhotoType(photoId)
                .orElseThrow(() -> new PhotoException());

        return PhotoType.ofDtype(dtype);
    }

    private void deletePhoto(Long photoId) {

        photoValidator.deletePhotoValidate(photoId);

        //TODO: 하위 객체까지 삭제되는지 확인하기
        photoRepository.delete(findById(photoId));
    }

    private String getUrlById(Long photoId) {

        return findById(photoId).getImgUrl();
    }

    private Photo findById(Long photoId) {

        return photoRepository.findById(photoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }
}
