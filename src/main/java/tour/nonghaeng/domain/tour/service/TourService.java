package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;
import tour.nonghaeng.global.validation.TourValidation;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourService {

    private final TourRepository tourRepository;
    private final TourValidation tourValidation;

    public void create(Seller seller, CreateTourDto dto) {

        tourValidation.createValidate(seller,dto);

        Tour createdTour = dto.toEntity(seller);
        tourRepository.save(createdTour);
    }

    public Page<TourSummaryDto> findAll(Pageable pageable) {

        Page<Tour> tourPages = tourRepository.findAll(pageable);
        Page<TourSummaryDto> summaryDtoPage = TourSummaryDto.toEntity(tourPages);

        return summaryDtoPage;

    }
}
