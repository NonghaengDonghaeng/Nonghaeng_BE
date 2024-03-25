package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tour.nonghaeng.domain.etc.photo.PhotoType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.photo.entity.ExperiencePhoto;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.validation.photo.ExperiencePhotoValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperiencePhotoService {

    private static final PhotoType PHOTO_TYPE = PhotoType.EXPERIENCE;

    private final ExperiencePhotoRepository experiencePhotoRepository;

    private final ExperienceService experienceService;
    private final AmazonS3Service amazonS3Service;

    private final ExperiencePhotoValidator experiencePhotoValidator;


    public Long upload(Seller seller, Long experienceId, MultipartFile imageFile) {

        Experience experience = experienceService.findById(experienceId);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createExperiencePhoto(experience, imgUrl).getId();

    }

    public void delete(Seller seller, Long experiencePhotoId) {

        experiencePhotoValidator.ownerValidate(seller, experiencePhotoId);

        amazonS3Service.deleteImage(PHOTO_TYPE, getUrlById(experiencePhotoId));

        deleteExperiencePhoto(experiencePhotoId);
    }

    private ExperiencePhoto createExperiencePhoto(Experience experience, String imgUrl) {

        return experiencePhotoRepository.save(ExperiencePhoto.builder()
                .experience(experience)
                .imgUrl(imgUrl)
                .build());
    }

    private void deleteExperiencePhoto(Long experiencePhotoId) {

        experiencePhotoRepository.delete(findById(experiencePhotoId));
    }

    private String getUrlById(Long experiencePhotoId) {

        return findById(experiencePhotoId).getImgUrl();
    }

    public ExperiencePhoto findById(Long experiencePhotoId) {

        return experiencePhotoRepository.findById(experiencePhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }
}
