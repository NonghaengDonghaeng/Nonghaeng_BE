package tour.nonghaeng.domain.photo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.photo.repo.ExperiencePhotoRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperiencePhotoService {

    private final ExperiencePhotoRepository experiencePhotoRepository;
}
