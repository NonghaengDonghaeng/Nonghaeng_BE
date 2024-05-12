package tour.nonghaeng.domain.tour.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.exception.RoomException;
import tour.nonghaeng.domain.room.exception.error.RoomErrorCode;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;
import tour.nonghaeng.domain.tour.exception.TourException;
import tour.nonghaeng.domain.tour.exception.error.TourErrorCode;

@Component
@RequiredArgsConstructor
public class TourValidator {

    private final TourRepository tourRepository;


    public void ownerValidate(Seller seller, Long tourId) {

        tourIdValidate(tourId);

        if (!seller.equals(tourRepository.findSellerByTourId(tourId).get())) {
            throw new RoomException(RoomErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void createValidate(Seller seller, CreateTourDto createTourDto) {

        if (tourRepository.existsBySeller(seller)) {
            throw new TourException(TourErrorCode.DUPLICATE_CREATE_TOUR_ERROR);
        }
        //dto 검사
    }

    public void pageValidate(Page<Tour> tourPages) {

        if (tourPages.isEmpty()) {
            throw new TourException(TourErrorCode.NO_TOUR_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
        //TODO: 대표사진이 없을때
//        for (Tour tour : tourPages.getContent()) {
//            if (tour.findRepresentPhoto().isEmpty()) {
//                throw new TourException(TourErrorCode.NO_REPRESENT_PHOTO_ERROR);
//            }
//        }
    }

    public void tourIdValidate(Long tourId) {

        if (!tourRepository.existsById(tourId)) {
            throw new TourException(TourErrorCode.WRONG_TOUR_ID_ERROR);
        }
    }
}
