package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;
import tour.nonghaeng.global.exception.SellerException;
import tour.nonghaeng.global.validation.TourValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourService {

    private final TourRepository tourRepository;
    private final TourValidator tourValidator;

    public void create(Seller seller, CreateTourDto dto) {

        tourValidator.createValidate(seller,dto);

        Tour createdTour = dto.toEntity(seller);
        tourRepository.save(createdTour);
    }

    public Page<TourSummaryDto> findAll(Pageable pageable) {

        Page<Tour> tourPages = tourRepository.findAll(pageable);

        tourValidator.pageValidate(tourPages);

        Page<TourSummaryDto> summaryDtoPage = TourSummaryDto.convert(tourPages);

        return summaryDtoPage;

    }

    public TourDetailDto findByTourId(Long tourId) {

        tourValidator.tourIdValidate(tourId);

        return TourDetailDto.convert(tourRepository.findById(tourId).get());
    }

    public Tour findBySeller(Seller seller) {

        return tourRepository.findBySeller(seller)
                .orElseThrow(() -> SellerException.EXCEPTION);
    }
}
