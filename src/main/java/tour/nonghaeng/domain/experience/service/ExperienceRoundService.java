package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.CreateExpRoundDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.repo.ExperienceRoundRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceRoundService {

    private final ExperienceRoundRepository experienceRoundRepository;

    public void addRounds(Experience experience, List<CreateExpRoundDto> dtos) {
        for (CreateExpRoundDto dto : dtos) {
            experience.addExperienceRound(createAndSaveRound(experience,dto));
        }
    }

    private ExperienceRound createAndSaveRound(Experience experience,CreateExpRoundDto dto) {

        ExperienceRound round = dto.toEntity(experience);
        return experienceRoundRepository.save(round);
    }


}
