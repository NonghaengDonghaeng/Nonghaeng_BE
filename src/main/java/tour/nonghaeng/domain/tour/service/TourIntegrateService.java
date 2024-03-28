package tour.nonghaeng.domain.tour.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.photo.service.TourPhotoService;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.validation.tour.TourValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TourIntegrateService {

    private final TourService tourService;
    private final TourPhotoService tourPhotoService;

    private final TourValidator tourValidator;

    public Page<TourSummaryDto> getTourSummaryDtoPage(Pageable pageable) {

        Page<Tour> tourPage = tourService.getTourPage(pageable);

        tourValidator.pageValidate(tourPage);

        Page<TourSummaryDto> dtoPage = tourPage.map(tour -> {

            TourSummaryDto dto = TourSummaryDto.toDto(tour);
            PhotoInfoDto photoDto = tourPhotoService.getRepresentTourPhotoDto(tour.getId());
            dto.addPhotoInfoDto(photoDto);

            return dto;
        });

        return dtoPage;
    }

    public TourDetailDto getTourDetailDto(Long tourId) {

        TourDetailDto tourDetailDto = TourDetailDto.toDto(tourService.findById(tourId));

        List<PhotoInfoDto> tourPhotoInfoListDto = tourPhotoService.getTourPhotoInfoListDto(tourId);

        tourDetailDto.addPhotoInfoDtoList(tourPhotoInfoListDto);

        return tourDetailDto;

    }
}
