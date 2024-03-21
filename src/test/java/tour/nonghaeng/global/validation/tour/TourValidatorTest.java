package tour.nonghaeng.global.validation.tour;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tour.nonghaeng.domain.etc.tour.TourType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;
import tour.nonghaeng.global.exception.TourException;
import tour.nonghaeng.global.exception.code.TourErrorCode;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@ExtendWith(MockitoExtension.class)
@DisplayName("투어검증 테스트")
class TourValidatorTest {

    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourValidator tourValidator;

    @Nested
    @DisplayName("createValidate() 테스트")
    class createValidate{

        @Test
        @DisplayName("예외1: tour 중복 생성")
        void tour중복생() {
            //given
            CreateTourDto dto = new CreateTourDto(TourType.CAMPING, "테스트 여행지", "여행지.com", "1", "1", "1", "1", "1", "1", "1");
            Seller seller = Seller.builder().build();
            Mockito.when(tourRepository.existsBySeller(any())).thenReturn(true);
            //when
            TourException tourException = assertThrows(TourException.class, () -> tourValidator.createValidate(seller, dto));
            //then
            assertThat(tourException.getBaseErrorCode()).isSameAs(TourErrorCode.DUPLICATE_CREATE_TOUR_ERROR);

            //when & then
            //assertThatThrownBy(() -> tourValidator.createValidate(seller, dto)).isInstanceOf(TourException.class);
        }

        @Test
        @DisplayName("정상")
        void 정상동작() {
            //given
            CreateTourDto dto = new CreateTourDto(TourType.CAMPING, "테스트 여행지", "여행지.com", "1", "1", "1", "1", "1", "1", "1");
            Seller seller = Seller.builder().build();
            Mockito.when(tourRepository.existsBySeller(any())).thenReturn(false);
            //when & then
            assertDoesNotThrow(() -> tourValidator.createValidate(seller, dto));

        }
    }

    @Nested
    @DisplayName("pageValidate() 테스트")
    class pageValidate{

        @Test
        @DisplayName("정상")
        void 정상동작() {
            //given
            Seller seller = makeTestSeller();
            Tour tour = makeTestTour(seller);
            Page<Tour> tourPage = new PageImpl<>(java.util.List.of(tour));
            //when & then
            assertDoesNotThrow(() -> tourValidator.pageValidate(tourPage));
        }

        @Test
        @DisplayName("예외1: 빈페이지")
        void 빈페이지() {
            //given
            Page<Tour> tourPage = new PageImpl<>(new ArrayList<>());
            //when
            TourException tourException = assertThrows(TourException.class, () -> tourValidator.pageValidate(tourPage));
            //then
            assertThat(tourException.getBaseErrorCode()).isSameAs(TourErrorCode.NO_TOUR_CONTENT_AT_CURRENT_PAGE_ERROR);

        }
    }

    @Nested
    @DisplayName("tourIdValidate() 테스트")
    class tourIdValidate{

        @Test
        @DisplayName("예외: id 존재 X")
        void tourIdValidate_CASE1() {
            //given
            Long fakeTourId = 1L;
            Mockito.when(tourRepository.existsById(any())).thenReturn(false);
            //when
            TourException exception = assertThrows(TourException.class, () -> tourValidator.tourIdValidate(fakeTourId));
            //then
            assertThat(exception.getBaseErrorCode()).isSameAs(TourErrorCode.WRONG_TOUR_ID_ERROR);

        }

        @Test
        @DisplayName("정상")
        void tourIdValidate_CASE2() {
            //given
            Long fakeTourId = 1L;
            Mockito.when(tourRepository.existsById(any())).thenReturn(true);
            //when & then
            assertDoesNotThrow(() -> tourValidator.tourIdValidate(fakeTourId));

        }
    }


}