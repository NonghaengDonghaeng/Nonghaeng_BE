package tour.nonghaeng.domain.like.service.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.data.repo.ExperienceLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExperienceLikeValidator {

    private final ExperienceLikeRepository experienceLikeRepository;


}
