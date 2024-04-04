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
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.entity.ExperiencePhoto;
import tour.nonghaeng.domain.photo.entity.Photo;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;
import tour.nonghaeng.domain.s3.AmazonS3Service;
import tour.nonghaeng.global.exception.PhotoException;
import tour.nonghaeng.global.validation.photo.ExperiencePhotoValidator;
import tour.nonghaeng.global.validation.photo.PhotoValidator;

import java.util.List;

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
    private final PhotoValidator photoValidator;

    public Long upload(Seller seller, Long experienceId, MultipartFile imageFile) {

        Experience experience = experienceService.findById(experienceId);

        String imgUrl = amazonS3Service.uploadImage(PHOTO_TYPE, imageFile);

        return createExperiencePhoto(seller, experience, imgUrl).getId();

    }

    public List<PhotoInfoDto> getExpPhotoInfoListDto(Long experienceId) {

        List<Photo> photoList = experiencePhotoRepository.findAllByExperience(experienceService.findById(experienceId));

        photoValidator.emptyPhotoListValidate(photoList);

        List<PhotoInfoDto> dto = PhotoInfoDto.toDtoList(photoList);

        return dto;
    }

    public PhotoInfoDto getRepresentExpPhotoDto(Long experienceId) {

        Experience experience = experienceService.findById(experienceId);

        experiencePhotoValidator.numOfRepresentPhotoValidate(experience);

        Long representId = experiencePhotoRepository.findRepresentativePhotoId(experience).get();

        return PhotoInfoDto.toDto(findById(experienceId));
    }

    public void changeRepresentativePhoto(Long expPhotoId) {

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

    private ExperiencePhoto createExperiencePhoto(Seller seller, Experience experience, String imgUrl) {

        ExperiencePhoto createdExperiencePhoto = ExperiencePhoto.builder()
                .experience(experience)
                .seller(seller)
                .imgUrl(imgUrl)
                .build();

        if (!experiencePhotoRepository.hasExactlyOneRepresentativePhoto(experience)) {
            createdExperiencePhoto.onRepresentative();
        }

        return experiencePhotoRepository.save(createdExperiencePhoto);
    }

    public ExperiencePhoto findById(Long experiencePhotoId) {

        return experiencePhotoRepository.findById(experiencePhotoId)
                .orElseThrow(() -> PhotoException.EXCEPTION);
    }
}
