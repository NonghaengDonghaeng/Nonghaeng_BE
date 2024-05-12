package tour.nonghaeng.domain.photo.service.sub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.experience.valid.ExperienceValidator;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.entity.ExperiencePhoto;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.exception.PhotoException;
import tour.nonghaeng.domain.photo.imageServer.service.ImageService;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;
import tour.nonghaeng.domain.photo.valid.ExperiencePhotoValidator;
import tour.nonghaeng.domain.photo.valid.PhotoValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperiencePhotoService implements PhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.EXPERIENCE;

    private final ExperiencePhotoRepository experiencePhotoRepository;

    private final ExperienceService experienceService;
    private final ImageService imageService;

    private final ExperiencePhotoValidator experiencePhotoValidator;
    private final ExperienceValidator experienceValidator;
    private final PhotoValidator photoValidator;


    @Override
    public PhotoType getType() {
        return PHOTO_TYPE;
    }

    @Override
    public void uploads(Seller seller, Long experienceId, List<MultipartFile> imageFiles) {

        experienceValidator.ownerValidate(seller,experienceId);

        Experience experience = experienceService.findById(experienceId);

        for(MultipartFile imageFile : imageFiles) {
            String imgUrl = imageService.uploadImage(PHOTO_TYPE, imageFile);
            createExperiencePhoto(seller, experience, imgUrl);
        }
    }

    private void createExperiencePhoto(Seller seller, Experience experience, String imgUrl) {

        ExperiencePhoto createdExperiencePhoto = ExperiencePhoto.builder()
                .experience(experience)
                .seller(seller)
                .imgUrl(imgUrl)
                .build();

        if (!experiencePhotoRepository.hasExactlyOneRepresentativePhoto(experience)) {
            createdExperiencePhoto.onRepresentative();
        }

        experiencePhotoRepository.save(createdExperiencePhoto);
    }


    @Override
    public List<PhotoInfoDto> getPhotoInfoListDto(Long experienceId) {

        List<Photo> photoList = experiencePhotoRepository.findAllByExperience(experienceService.findById(experienceId));

        photoValidator.emptyPhotoListValidate(photoList);

        return PhotoInfoDto.toDtoList(photoList);
    }


    @Override
    public void changeRepresentativePhoto(Seller seller,Long expPhotoId) {


        experiencePhotoValidator.ownerValidate(seller, expPhotoId);

        ExperiencePhoto experiencePhoto = findById(expPhotoId);
        Experience experience = experiencePhoto.getExperience();

        experiencePhotoValidator.numOfRepresentPhotoValidate(experience);

        experiencePhotoRepository.findRepresentativePhotoId(experience)
                .ifPresent(id->{
                    ExperiencePhoto beforeRepresentativePhoto = findById(id);
                    beforeRepresentativePhoto.offRepresentative();
                    experiencePhotoRepository.save(beforeRepresentativePhoto);
                });

        experiencePhoto.onRepresentative();

        experiencePhotoRepository.save(experiencePhoto);

    }

    @Override
    public void delete(Seller seller,Long photoId) {

        photoValidator.deletePhotoValidate(photoId);
        experiencePhotoValidator.ownerValidate(seller,photoId);

        ExperiencePhoto experiencePhoto = findById(photoId);

        imageService.deleteImage(PHOTO_TYPE, experiencePhoto.getImgUrl());

        experiencePhotoRepository.delete(experiencePhoto);
    }

    private ExperiencePhoto findById(Long experiencePhotoId) {

        return experiencePhotoRepository.findById(experiencePhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }

    private Photo findPhotoById(Long experiencePhotoId) {
        return experiencePhotoRepository.findPhotoById(experiencePhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }
}
