package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.tour.repo.TourRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourService {

    private final TourRepository tourRepository;

}
