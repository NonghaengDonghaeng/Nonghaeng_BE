package tour.nonghaeng.global.validation.experience;

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
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tour.nonghaeng.global.testEntity.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@ExtendWith(MockitoExtension.class)
@DisplayName("체험검증 테스트")
class ExperienceValidatorTest {

    @Mock
    private ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceValidator experienceValidator;

    @Nested
    @DisplayName("ownerValidate() 테스트")
    class ownerValidate {

        @Test
        @DisplayName("예외1: id 존재X")
        void id존재X() {
            //given
            Long fakeNotExistId = 1L;
            Mockito.when(experienceRepository.existsById(fakeNotExistId)).thenReturn(false);
            Seller seller = makeTestSeller();
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceValidator.ownerValidate(seller, fakeNotExistId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.NO_EXIST_EXPERIENCE_BY_EXPERIENCE_ID_ERROR);
        }

        @Test
        @DisplayName("예외2: seller 불일치")
        void seller불일치() {
            //given
            Long fakeExistId = 1L;
            Seller seller1 = makeTestSeller();
            Seller seller2 = makeTestSeller();
            Mockito.when(experienceRepository.existsById(fakeExistId)).thenReturn(true);
            Mockito.when(experienceRepository.findSellerByExperienceId(fakeExistId)).thenReturn(Optional.ofNullable(seller1));
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceValidator.ownerValidate(seller2, fakeExistId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

        @Test
        @DisplayName("정상: id 존재, seller 일치")
        void 정상동() {
            //given
            Long fakeExistId = 1L;
            Seller seller1 = makeTestSeller();

            Mockito.when(experienceRepository.existsById(fakeExistId)).thenReturn(true);
            Mockito.when(experienceRepository.findSellerByExperienceId(fakeExistId)).thenReturn(Optional.ofNullable(seller1));
            //when & then
            assertDoesNotThrow(() -> experienceValidator.ownerValidate(seller1, fakeExistId));
        }
    }


    @Nested
    @DisplayName("pageValidate() 테스트")
    class pageValidate {

        @Test
        @DisplayName("예외1: 빈페이지")
        void 빈페이지() {
            //given
            Page<Experience> emptyPage = new PageImpl<>(new ArrayList<>());
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceValidator.pageValidate(emptyPage)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.NO_EXPERIENCE_CONTENT_AT_CURRENT_PAGE_ERROR);
        }

        @Test
        @DisplayName("정상성")
        void 정상동작() {
            //given
            Seller seller = makeTestSeller();
            Tour tour = makeTestTour(seller);
            Experience experience = makeTestExperience(tour);
            Page<Experience> notEmptyPage = new PageImpl<>(List.of(experience));
            //when & then
            assertDoesNotThrow(() -> experienceValidator.pageValidate(notEmptyPage));
        }
    }

}