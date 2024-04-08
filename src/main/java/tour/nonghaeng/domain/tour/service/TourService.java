package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.tour.TourType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.speciification.TourSpecification;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;
import tour.nonghaeng.global.exception.SellerException;
import tour.nonghaeng.global.exception.TourException;
import tour.nonghaeng.global.exception.code.TourErrorCode;
import tour.nonghaeng.global.validation.tour.TourValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourService {

    private final TourRepository tourRepository;

    private final TourValidator tourValidator;


    public Long createTour(Seller seller, CreateTourDto dto) {

        tourValidator.createValidate(seller,dto);

        Tour createdTour = dto.toEntity(seller);

        return tourRepository.save(createdTour).getId();
    }

    public Page<Tour> getTourPage(Pageable pageable,String keyword,AreaCode areaCode,TourType tourType) {

        Specification<Tour> spec = TourSpecification.buildSpecification(keyword, areaCode, tourType);

        Page<Tour> tourPage = tourRepository.findAll(spec, pageable);

        return tourPage;
    }

    public Page<TourSummaryDto> getTourSummaryDtoPage(Pageable pageable, String keyword, AreaCode areaCode, TourType tourType) {

        Page<Tour> tourPage = getTourPage(pageable,keyword,areaCode,tourType);

        tourValidator.pageValidate(tourPage);

        Page<TourSummaryDto> summaryDtoPage = TourSummaryDto.toPageDto(tourPage);

        return summaryDtoPage;
    }

    public Page<Tour> findAllTourPageWithRoom(Pageable pageable) {

        return tourRepository.findAllByRoomsIsNotEmpty(pageable);
    }

    public TourDetailDto getTourDetailDto(Long tourId) {

        return TourDetailDto.toDto(findById(tourId));
    }

    public Tour findById(Long tourId) {

        return tourRepository.findById(tourId)
                .orElseThrow(() -> new TourException(TourErrorCode.WRONG_TOUR_ID_ERROR));
    }

    public Tour findBySeller(Seller seller) {

        return tourRepository.findBySeller(seller)
                .orElseThrow(() -> SellerException.EXCEPTION);
    }
}
