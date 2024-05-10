package tour.nonghaeng.domain.like.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.like.repo.ReviewLikeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewLikeValidator {

    private final ReviewLikeRepository reviewLikeRepository;


}
