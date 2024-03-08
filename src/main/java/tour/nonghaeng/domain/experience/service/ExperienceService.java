package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.AddExpOpenDateDto;
import tour.nonghaeng.domain.experience.dto.CreateExpDto;
import tour.nonghaeng.domain.experience.dto.CreateExpRoundDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.validation.ExperienceValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceValidator experienceValidator;

    private final ExperienceRoundService experienceRoundService;
    private final ExperienceOpenDateService experienceOpenDateService;

    private final TourService tourService;

    public Long add(Seller seller, CreateExpDto dto) {
        //검증

        //체험 엔티티 생성
        Tour tour = tourService.findBySeller(seller);
        Experience experience = dto.toEntity(seller, tour);

        //체험회차 엔티티 생성,저장 후 체험엔티티에 연결
        experienceRoundService.addRounds(experience,dto.expRoundDtoList());

        return experienceRepository.save(experience).getId();

    }

    public Long addOnlyOpenDates(Long experienceId, List<AddExpOpenDateDto> dtoList) {

        //TODO: 검증 로직 추가 (이미 등록된 날짜가 있는지, 과거날짜가 아닌지 확인)

        Experience experience = findById(experienceId);

        experienceOpenDateService.addOpenDates(experience, dtoList);

        return experienceRepository.save(experience).getId();

    }

    public Long addOnlyRounds(Long experienceId, List<CreateExpRoundDto> dtoList) {

        //TODO: 검증 로직 추가 (곂치는 시간이 있는지)

        Experience experience = findById(experienceId);

        experienceRoundService.addRounds(experience,dtoList);

        return experienceRepository.save(experience).getId();
    }

    public Experience findById(Long experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }

    public Experience findBySeller(Seller seller) {
        return experienceRepository.findBySeller(seller)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }

}
