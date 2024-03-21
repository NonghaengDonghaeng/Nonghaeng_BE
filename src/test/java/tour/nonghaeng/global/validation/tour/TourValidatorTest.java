package tour.nonghaeng.global.validation.tour;

import org.junit.jupiter.api.DisplayName;
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
class TourValidatorTest {

    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourValidator tourValidator;

    @Test
    @DisplayName("이미 여행지를 등록한 판매자일때 예외발생")
    void createValidate_CASE1() {
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
    @DisplayName("이미 여행지를 등록한 판매자아니면 예외발생 X")
    void createValidate_CASE2() {
        //given
        CreateTourDto dto = new CreateTourDto(TourType.CAMPING, "테스트 여행지", "여행지.com", "1", "1", "1", "1", "1", "1", "1");
        Seller seller = Seller.builder().build();
        Mockito.when(tourRepository.existsBySeller(any())).thenReturn(false);
        //when & then
        assertDoesNotThrow(() -> tourValidator.createValidate(seller, dto));

    }

    @Test
    @DisplayName("안에 내용이 비어있지 않을땐 예외발생 X")
    void pageValidate_CASE1() {
        //given
        Seller seller = makeTestSeller();
        Tour tour = makeTestTour(seller);
        Page<Tour> tourPage = new PageImpl<>(java.util.List.of(tour));
        //when & then
        assertDoesNotThrow(() -> tourValidator.pageValidate(tourPage));


    }
    @Test
    @DisplayName("안에 내용이 비어있을 땐 예외 발생")
    void pageValidate_CASE2() {
        //given
        Page<Tour> tourPage = new PageImpl<>(new ArrayList<>());
        //when
        TourException tourException = assertThrows(TourException.class, () -> tourValidator.pageValidate(tourPage));
        //then
        assertThat(tourException.getBaseErrorCode()).isSameAs(TourErrorCode.NO_TOUR_CONTENT_AT_CURRENT_PAGE_ERROR);

    }

    @Test
    @DisplayName("존재하지 않는 여행지아이디에 관해서 예외발생")
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
    @DisplayName("존재하는 여행지아이디에 관해서 예외발생 X")
    void tourIdValidate_CASE2() {
        //given
        Long fakeTourId = 1L;
        Mockito.when(tourRepository.existsById(any())).thenReturn(true);
        //when & then
        assertDoesNotThrow(() -> tourValidator.tourIdValidate(fakeTourId));

    }
}