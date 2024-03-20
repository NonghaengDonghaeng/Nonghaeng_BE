package tour.nonghaeng.domain.experience.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.experience.TestExperienceCloseDate.*;
import static tour.nonghaeng.global.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.tour.TestTour.makeTestTour;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExperienceCloseDateRepositoryTest {

    @Autowired
    private ExperienceCloseDateRepository experienceCloseDateRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    private static Seller seller;
    private static Tour tour;
    private static Experience experience;

    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);

        experience = makeTestExperience(tour);
        experienceRepository.save(experience);
    }

    @Test
    @DisplayName("저장 및 조회")
    void 저장및조회() {
        //given
        ExperienceCloseDate experienceCloseDate = makeTestExperienceCloseDate(experience);
        ExperienceCloseDate saved = experienceCloseDateRepository.save(experienceCloseDate);
        //when
        Optional<ExperienceCloseDate> byId = experienceCloseDateRepository.findById(experienceCloseDate.getId());
        //then
        byId.ifPresent(experienceCloseDate1 -> assertThat(experienceCloseDate1).isSameAs(saved));

    }

    @Test
    @DisplayName("existsByExperienceAndCloseDate: 존재할때와 존재하지 않을때 모두")
    void existsByExperienceAndCloseDate() {
        //given
        ExperienceCloseDate experienceCloseDate = makeTestExperienceCloseDate(experience);

        experienceCloseDateRepository.save(experienceCloseDate);
        LocalDate localDate1 = experienceCloseDate.getCloseDate();
        LocalDate localDate2 = LocalDate.of(EXPERIENCE_CLOSE_DATE_YEAR, EXPERIENCE_CLOSE_DATE_MONTH+1, EXPERIENCE_CLOSE_DATE_DAY+1);
        //when
        boolean result1 = experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, localDate1);
        boolean result2 = experienceCloseDateRepository.existsByExperienceAndCloseDate(experience, localDate2);
        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();

    }

    @Test
    @DisplayName("findByExperienceAndCloseDate: 미운영날짜와 체험으로 조회하기")
    @Order(1)
    void findByExperienceAndCloseDate() {
        //given
        ExperienceCloseDate experienceCloseDate1 = makeTestExperienceCloseDate(experience);
        ExperienceCloseDate experienceCloseDate2 = makeTestExperienceCloseDate(experience);
        ExperienceCloseDate experienceCloseDate3 = makeTestExperienceCloseDate(experience);
        ExperienceCloseDate experienceCloseDate4 = makeTestExperienceCloseDate(experience);

        experienceCloseDateRepository.save(experienceCloseDate1);
        experienceCloseDateRepository.save(experienceCloseDate2);
        experienceCloseDateRepository.save(experienceCloseDate3);
        experienceCloseDateRepository.save(experienceCloseDate4);

        LocalDate findLocalDate1 = LocalDate.of(EXPERIENCE_CLOSE_DATE_YEAR, EXPERIENCE_CLOSE_DATE_MONTH, EXPERIENCE_CLOSE_DATE_DAY);
        LocalDate findLocalDate2 = LocalDate.of(EXPERIENCE_CLOSE_DATE_YEAR, EXPERIENCE_CLOSE_DATE_MONTH, EXPERIENCE_CLOSE_DATE_DAY+2);
        //when
        Optional<ExperienceCloseDate> byExperienceAndCloseDate1 =
                experienceCloseDateRepository.findByExperienceAndCloseDate(experience, findLocalDate1);

        Optional<ExperienceCloseDate> byExperienceAndCloseDate2 =
                experienceCloseDateRepository.findByExperienceAndCloseDate(experience, findLocalDate2);
        //then
        byExperienceAndCloseDate1.ifPresent(experienceCloseDate -> assertThat(experienceCloseDate).isSameAs(experienceCloseDate));
        byExperienceAndCloseDate2.ifPresent(experienceCloseDate -> assertThat(experienceCloseDate).isSameAs(experienceCloseDate3));
    }
}