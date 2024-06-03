package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.tour.TourType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.presentation.exception.SellerException;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.dto.speciification.TourSpecification;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.domain.tour.presentation.exception.TourException;
import tour.nonghaeng.domain.tour.presentation.exception.error.TourErrorCode;
import tour.nonghaeng.domain.tour.data.repo.TourRepository;
import tour.nonghaeng.domain.tour.service.valid.TourValidator;
import tour.nonghaeng.global.auth.AuthValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourService {

    private final TourRepository tourRepository;

    private final TourValidator tourValidator;
    private final AuthValidator authValidator;




    public Long createTour(Member seller, CreateTourDto dto) {


        tourValidator.createValidate(seller,dto);

        Tour createdTour = dto.toEntity(authValidator.sellerValidate(seller));

        return tourRepository.save(createdTour).getId();
    }



    public Page<Tour> getTourPage(Pageable pageable,String keyword,List<AreaCode> areaCodes,TourType tourType) {

        Specification<Tour> spec = TourSpecification.buildSpecification(keyword, areaCodes, tourType);

        return tourRepository.findAll(spec, pageable);
    }



    public Page<TourSummaryDto> getTourSummaryDtoPage(Pageable pageable, String keyword, List<AreaCode> areaCodes, TourType tourType) {

        Page<Tour> tourPage = getTourPage(pageable,keyword,areaCodes,tourType);

        tourValidator.pageValidate(tourPage);

        return TourSummaryDto.toPageDto(tourPage);
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



    public Tour findBySeller(Member seller) {

        return tourRepository.findBySeller(authValidator.sellerValidate(seller))
                .orElseThrow(() -> SellerException.EXCEPTION);
    }
}
