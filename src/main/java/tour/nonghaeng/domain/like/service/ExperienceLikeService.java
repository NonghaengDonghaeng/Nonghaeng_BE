package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.like.entity.ExperienceLike;
import tour.nonghaeng.domain.like.repo.ExperienceLikeRepository;
import tour.nonghaeng.domain.like.valid.ExperienceLikeValidator;
import tour.nonghaeng.domain.member.entity.User;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceLikeService {

    private final ExperienceLikeRepository experienceLikeRepository;

    private final ExperienceLikeValidator experienceLikeValidator;

    private final ExperienceService experienceService;

    public void createLike(User user, Long experienceId) {

        Experience experience = experienceService.findById(experienceId);

        experienceLikeValidator.createLikeValidate(user.getId(), experienceId);

        experienceLikeRepository.save(ExperienceLike.builder().user(user).experience(experience).build());
    }
}
