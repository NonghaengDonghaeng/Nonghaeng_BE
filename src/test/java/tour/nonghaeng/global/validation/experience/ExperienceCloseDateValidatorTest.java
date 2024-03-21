package tour.nonghaeng.global.validation.experience;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tour.nonghaeng.domain.experience.dto.AddExpCloseDateDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.repo.ExperienceCloseDateRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tour.nonghaeng.global.testEntity.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@ExtendWith(MockitoExtension.class)
@DisplayName("체험미운영날짜검증 테스트")
class ExperienceCloseDateValidatorTest {

    @Mock
    private ExperienceCloseDateRepository experienceCloseDateRepository;

    @InjectMocks
    private ExperienceCloseDateValidator experienceCloseDateValidator;

    private static Seller seller;
    private static Tour tour;
    private static Experience experience;

    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        tour = makeTestTour(seller);
        experience = makeTestExperience(tour);
    }

    @Nested
    @DisplayName("defaultCloseDateDtoValidate() 테스트")
    class defaultCloseDateDtoValidate {
        @Test
        @DisplayName("예외1: 과거날짜 존재")
        void 과거날짜존재() {
            //given
            AddExpCloseDateDto dto = new AddExpCloseDateDto(LocalDate.of(2023, 5, 1));
            List<AddExpCloseDateDto> dtoList = List.of(dto);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceCloseDateValidator.defaultCloseDateDtoValidate(dtoList)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.PAST_EXPERIENCE_CLOSE_DATE_ADD_ERROR);
        }
        @Test
        @DisplayName("예외1: 중복된 날짜 존재")
        void 중복된날짜존재() {
            //given
            AddExpCloseDateDto dto1 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 5));
            AddExpCloseDateDto dto2 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 6));
            AddExpCloseDateDto dto3 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 5));
            List<AddExpCloseDateDto> dtoList = List.of(dto1, dto2, dto3);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceCloseDateValidator.defaultCloseDateDtoValidate(dtoList)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.DUPLICATE_EXPERIENCE_CLOSE_DATE_ADD_ERROR);
        }

        @Test
        @DisplayName("정상")
        void 과거도아니고중복도안됨() {
            //given
            AddExpCloseDateDto dto1 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 5));
            AddExpCloseDateDto dto2 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 6));
            AddExpCloseDateDto dto3 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 7));
            List<AddExpCloseDateDto> dtoList = List.of(dto1, dto2, dto3);
            //when
            assertDoesNotThrow(() -> experienceCloseDateValidator.defaultCloseDateDtoValidate(dtoList));
        }
    }

    @Nested
    @DisplayName("createAndSaveValidate() 테스트")
    class createAndSaveValidate{
        @Test
        @DisplayName("예외1: 이미 미운영날짜 등록됨")
        void createAndSaveValidate() {
            //given
            LocalDate registeredDate = LocalDate.of(2024, 5, 5);
            AddExpCloseDateDto dto = new AddExpCloseDateDto(registeredDate);

            Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, registeredDate)).thenReturn(true);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceCloseDateValidator.createAndSaveValidate(experience, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.ALREADY_EXIST_EXPERIENCE_CLOSE_DATE_ADD_ERROR);
        }

        @Test
        @DisplayName("예외2: 운영기간에 안속함")
        void createAndSaveValidate2() {
            //given
            LocalDate notRunningDate = LocalDate.of(2025, 1, 1);
            AddExpCloseDateDto dto = new AddExpCloseDateDto(notRunningDate);

            Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience,notRunningDate)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceCloseDateValidator.createAndSaveValidate(experience, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.ALREADY_NOT_RUNNING_DATE_ADD_ERROR);

        }

        @Test
        @DisplayName("정상")
        void createAndSaveValidate3() {
            //given
            LocalDate closeDate = LocalDate.of(2024, 7, 7);
            AddExpCloseDateDto dto = new AddExpCloseDateDto(closeDate);

            Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, closeDate)).thenReturn(false);
            //when & then
            assertDoesNotThrow(() -> experienceCloseDateValidator.createAndSaveValidate(experience, dto));
        }
    }

    @Nested
    @DisplayName("removeDtoListValidate() 테스트")
    class removeDtoListValidate{

        @Test
        @DisplayName("예외1: 운영기간에 속하지 않음")
        void removeDtoListValidate1() {
            //given
            LocalDate notRunningDate = LocalDate.of(2025, 1, 1);
            AddExpCloseDateDto dto1 = new AddExpCloseDateDto(notRunningDate);
            AddExpCloseDateDto dto2 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 6));
            AddExpCloseDateDto dto3 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 7));
            List<AddExpCloseDateDto> dtoList = List.of(dto1, dto2, dto3);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceCloseDateValidator.removeDtoListValidate(experience, dtoList))
                    .getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.WRONG_DATE_PARAMETERS_BY_NOT_RUNNING_DATE);
        }

        @Test
        @DisplayName("예외2: 미운영 리스트에 들어가지 있지 않음")
        void removeDtoListValidate2() {
            //given
            LocalDate notRegisterDate = LocalDate.of(2024, 5, 1);
            AddExpCloseDateDto dto1 = new AddExpCloseDateDto(notRegisterDate);
            AddExpCloseDateDto dto2 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 6));
            AddExpCloseDateDto dto3 = new AddExpCloseDateDto(LocalDate.of(2024, 5, 7));
            List<AddExpCloseDateDto> dtoList = List.of(dto1, dto2, dto3);

            Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, notRegisterDate)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceCloseDateValidator.removeDtoListValidate(experience, dtoList)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.NOT_EXIST_EXPERIENCE_CLOSE_DATE_ERROR);
        }

        @Test
        @DisplayName("정상")
        void removeDtoListValidate3() {
            //given
            LocalDate removeDate = LocalDate.of(2024, 5, 1);
            AddExpCloseDateDto dto = new AddExpCloseDateDto(removeDate);
            List<AddExpCloseDateDto> dtoList = List.of(dto);

            Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, removeDate)).thenReturn(true);
            //when & then
            assertDoesNotThrow(() -> experienceCloseDateValidator.removeDtoListValidate(experience, dtoList));

        }
    }

   @Nested
   @DisplayName("isOpenDateParameterValidate() 테스트")
   class isOpenDateParameterValidate{

       @Test
       @DisplayName("예외1: 오늘보다 이전임")
       void isOpenDateParameterValidate1() {
           //given
           LocalDate beforeDate = LocalDate.of(2024, 3, 1);

           //when
           BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                   () -> experienceCloseDateValidator.isOpenDateParameterValidate(experience, beforeDate))
                   .getBaseErrorCode();
           //then
           assertThat(errorCode).isSameAs(ExperienceErrorCode.WRONG_DATE_PARAMETER_BY_PAST_DATE);
       }
       @Test
       @DisplayName("예외2: 운영기간에 안속함")
       void isOpenDateParameterValidate2() {
           //given
           LocalDate notRunningDate = LocalDate.of(2025, 1, 1);
           //when
           BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                   () -> experienceCloseDateValidator.isOpenDateParameterValidate(experience, notRunningDate)).getBaseErrorCode();
           //then
           assertThat(errorCode).isSameAs(ExperienceErrorCode.WRONG_DATE_PARAMETERS_BY_NOT_RUNNING_DATE);
       }
       @Test
       @DisplayName("예외3: 미운영리스트에 들어가있음")
       void isOpenDateParameterValidate3() {
           //given
           LocalDate registerDate = LocalDate.of(2024, 5, 1);

           Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, registerDate)).thenReturn(true);
           //when
           BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                   () -> experienceCloseDateValidator.isOpenDateParameterValidate(experience, registerDate)).getBaseErrorCode();
           //then
           assertThat(errorCode).isSameAs(ExperienceErrorCode.WRONG_DATE_PARAMETERS_BY_NOT_RUNNING_DATE);
       }
       @Test
       @DisplayName("정상")
       void isOpenDateParameterValidate4() {
           //given
           LocalDate openDate = LocalDate.of(2024, 5, 5);
           Mockito.when(experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, openDate)).thenReturn(false);
           //when & then
           assertDoesNotThrow(() -> experienceCloseDateValidator.isOpenDateParameterValidate(experience, openDate));
       }
   }


}