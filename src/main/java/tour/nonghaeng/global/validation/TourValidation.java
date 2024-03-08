package tour.nonghaeng.global.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;
import tour.nonghaeng.global.exception.GlobalException;
import tour.nonghaeng.global.exception.TourException;
import tour.nonghaeng.global.exception.code.TourErrorCode;

@RequiredArgsConstructor
@Component
public class TourValidation {

    private final TourRepository tourRepository;

    public void createValidate(Seller seller, CreateTourDto createTourDto) {
        //이미 관광을 등록한 seller 인지 검증
        if (tourRepository.existsBySeller(seller)) {
            throw new TourException(TourErrorCode.DUPLICATE_CREATE_TOUR);
        }
        //dto 검사
    }

    public void pageValidate(Page<Tour> tourPages) {
        if (tourPages.isEmpty()) {
            throw new TourException(TourErrorCode.NO_TOUR_CONTENT_AT_CURRENT_PAGE);
        }
    }

}
