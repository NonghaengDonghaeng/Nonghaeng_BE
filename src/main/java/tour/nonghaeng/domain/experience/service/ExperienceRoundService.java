package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
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

    public void addRounds(Experience experience, List<AddExpRoundDto> dtos) {
        for (AddExpRoundDto dto : dtos) {
            experience.addExperienceRound(createAndSaveRound(experience,dto));
        }
    }

    private ExperienceRound createAndSaveRound(Experience experience, AddExpRoundDto dto) {

        ExperienceRound round = dto.toEntity(experience);
        return experienceRoundRepository.save(round);
    }


}
