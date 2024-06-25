package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.presentation.exception.SellerException;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.domain.tour.data.repo.TourRepository;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.TourSpecDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.presentation.exception.TourException;
import tour.nonghaeng.domain.tour.presentation.exception.error.TourErrorCode;
import tour.nonghaeng.domain.tour.service.valid.TourValidator;
import tour.nonghaeng.global.auth.AuthValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    private final TourValidator tourValidator;
    private final AuthValidator authValidator;




    @Override
    public Tour findById(Long tourId) {

        return tourRepository.findById(tourId)
                .orElseThrow(() -> new TourException(TourErrorCode.WRONG_TOUR_ID_ERROR));
    }


    @Override
    public Tour create(Member seller, CreateTourDto dto) {


        tourValidator.createValidate(seller,dto);

        Tour createdTour = dto.toEntity(authValidator.sellerValidate(seller));

        return tourRepository.save(createdTour);
    }


    @Override
    public TourDetailDto getDetailDto(Long tourId) {
        return TourDetailDto.toDto(findById(tourId));
    }


    @Override
    public Page<TourSummaryDto> getSummaryDtoPage(Pageable pageable, TourSpecDto specDto) {

        Page<Tour> tourPage = null;

        if(specDto == null) {

            tourPage=tourRepository.findAll(pageable);

            tourValidator.pageValidate(tourPage);

            return TourSummaryDto.toPageDto(tourPage);
        }

        tourPage = tourRepository.findAll(specDto.buildSpecification(), pageable);

        tourValidator.pageValidate(tourPage);

        return TourSummaryDto.toPageDto(tourPage);
    }



    @Override
    public Tour findBySeller(Member seller) {

        return tourRepository.findBySeller(authValidator.sellerValidate(seller))
                .orElseThrow(() -> SellerException.EXCEPTION);
    }

    @Override
    public void plusLikes(Tour tour) {

        tour.plusLikes();
        tourRepository.save(tour);
    }

    @Override
    public void minusLikes(Tour tour) {

        tour.minusLikes();
        tourRepository.save(tour);
    }
}
