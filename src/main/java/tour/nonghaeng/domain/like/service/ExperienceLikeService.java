package tour.nonghaeng.domain.like.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.etc.like.LikeType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.like.entity.ExperienceLike;
import tour.nonghaeng.domain.like.repo.ExperienceLikeRepository;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.global.auth.valid.AuthValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceLikeService implements LikeService {

    private static final LikeType LIKE_TYPE = LikeType.EXPERIENCE;

    private final ExperienceLikeRepository experienceLikeRepository;

    private final ExperienceService experienceService;

    private final AuthValidator authValidator;


    @Override
    public LikeType getType() {
        return LIKE_TYPE;
    }

    @Override
    public boolean clickLike(Member user, Long experienceId) {

        Experience experience = experienceService.findById(experienceId);

        Optional<ExperienceLike> maybeExperienceLike = experienceLikeRepository.findByUserAndExperience(authValidator.userValidate(user), experience);

        return maybeExperienceLike.map(this::offLike).orElseGet(() -> onLike(user, experience));
    }

    private boolean onLike(Member user, Experience experience) {

        experienceLikeRepository.save(ExperienceLike.builder()
                .user(authValidator.userValidate(user))
                .experience(experience)
                .build()
        );
        return true;
    }

    private boolean offLike(ExperienceLike experienceLike) {

        experienceLikeRepository.delete(experienceLike);
        return false;
    }

}
