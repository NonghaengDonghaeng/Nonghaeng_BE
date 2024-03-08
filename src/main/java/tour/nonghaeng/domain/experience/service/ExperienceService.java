package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.CreateExpDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.validation.ExperienceValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceValidator experienceValidator;

    private final TourService tourService;

    public Long add(Seller seller, CreateExpDto dto) {
        //검증

        Tour tour = tourService.findBySeller(seller);
        Experience experience = dto.toEntity(seller, tour);

        return experienceRepository.save(experience).getId();

    }
}
