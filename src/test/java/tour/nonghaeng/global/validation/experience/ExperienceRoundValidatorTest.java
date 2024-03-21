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
import tour.nonghaeng.domain.experience.dto.AddExpRoundDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.repo.ExperienceRoundRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tour.nonghaeng.global.testEntity.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.testEntity.experience.TestExperienceRound.makeTestExperienceRound;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@ExtendWith(MockitoExtension.class)
@DisplayName("체험회차검증 테스트")
class ExperienceRoundValidatorTest {

    @Mock
    private ExperienceRoundRepository experienceRoundRepository;

    @InjectMocks
    private ExperienceRoundValidator experienceRoundValidator;

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
    @DisplayName("createAndSaveValidate() 테스트")
    class createAndSaveValidate{
        @Test
        @DisplayName("정상")
        void createAndSaveValidate() {
            //given
            LocalTime start = LocalTime.of(16, 0);
            LocalTime end = LocalTime.of(18, 0);
            AddExpRoundDto dto = new AddExpRoundDto(start, end, 10);

            ExperienceRound experienceRound1 = makeTestExperienceRound(experience, 8);
            ExperienceRound experienceRound2 = makeTestExperienceRound(experience, 10);
            ExperienceRound experienceRound3 = makeTestExperienceRound(experience, 12);
            List<ExperienceRound> experienceRoundList = List.of(experienceRound1, experienceRound2, experienceRound3);

            Mockito.when(experienceRoundRepository.findAllByExperienceOrderByStartTime(experience)).thenReturn(experienceRoundList);
            //when & then
            assertDoesNotThrow(() -> experienceRoundValidator.createAndSaveValidate(experience, dto));

        }

        @Test
        @DisplayName("예외1: 이미 등록된 회차에 겹침")
        void 일부시간겹침() {
            //given
            LocalTime start = LocalTime.of(9, 0);
            LocalTime end = LocalTime.of(11, 0);
            AddExpRoundDto addExpRoundDto = new AddExpRoundDto(start, end, 10);

            ExperienceRound experienceRound1 = makeTestExperienceRound(experience, 8);
            ExperienceRound experienceRound2 = makeTestExperienceRound(experience, 10);
            ExperienceRound experienceRound3 = makeTestExperienceRound(experience, 12);
            List<ExperienceRound> experienceRoundList = List.of(experienceRound1, experienceRound2, experienceRound3);

            Mockito.when(experienceRoundRepository.findAllByExperienceOrderByStartTime(experience)).thenReturn(experienceRoundList);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceRoundValidator.createAndSaveValidate(experience, addExpRoundDto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.OVERLAPS_ROUND_TIME_ADD_ERROR);
        }

        @Test
        @DisplayName("예외2: 존재하는 회차를 포함하는 시간")
        void 존재하는회차를포함하는시간() {
            //given
            LocalTime start = LocalTime.of(6, 0);
            LocalTime end = LocalTime.of(12, 0);
            AddExpRoundDto addExpRoundDto = new AddExpRoundDto(start, end, 10);

            ExperienceRound experienceRound1 = makeTestExperienceRound(experience, 8);
            ExperienceRound experienceRound2 = makeTestExperienceRound(experience, 10);
            ExperienceRound experienceRound3 = makeTestExperienceRound(experience, 12);
            List<ExperienceRound> experienceRoundList = List.of(experienceRound1, experienceRound2, experienceRound3);

            Mockito.when(experienceRoundRepository.findAllByExperienceOrderByStartTime(experience)).thenReturn(experienceRoundList);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceRoundValidator.createAndSaveValidate(experience, addExpRoundDto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.OVERLAPS_ROUND_TIME_ADD_ERROR);
        }
    }
}