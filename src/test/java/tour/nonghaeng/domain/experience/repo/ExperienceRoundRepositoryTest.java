package tour.nonghaeng.domain.experience.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.testEntity.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.testEntity.experience.TestExperienceRound.makeTestExperienceRound;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExperienceRoundRepositoryTest {

    @Autowired
    private ExperienceRoundRepository experienceRoundRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    private static Seller seller;
    private static Tour tour;
    private static Experience experience;
    private static ExperienceRound experienceRound;

    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);

        experience = makeTestExperience(tour);
        experienceRepository.save(experience);

        experienceRound = makeTestExperienceRound(experience);
    }

    @Test
    @DisplayName("저장 및 조회")
    void 저장및조회() {
        //given
        ExperienceRound savedExperienceRound = experienceRoundRepository.save(experienceRound);
        //when
        Optional<ExperienceRound> byId = experienceRoundRepository.findById(savedExperienceRound.getId());
        //then
        byId.ifPresent(findExperienceRound -> assertThat(findExperienceRound).isSameAs(savedExperienceRound));
    }

    @Test
    @DisplayName("findAllByExperienceOrderByStartTime: 시작시간을 이른순으로 찾기")
    @Order(1)
    void findAllByExperienceOrderByStartTime() {
        //given
        ExperienceRound experienceRound1 = makeTestExperienceRound(experience);
        ExperienceRound experienceRound2 = makeTestExperienceRound(experience);
        ExperienceRound experienceRound3 = makeTestExperienceRound(experience);

        experienceRoundRepository.save(experienceRound);
        experienceRoundRepository.save(experienceRound1);
        experienceRoundRepository.save(experienceRound2);
        experienceRoundRepository.save(experienceRound3);
        //when
        List<ExperienceRound> list = experienceRoundRepository.findAllByExperienceOrderByStartTime(experience).get();
        //then
        assertThat(list.size()).isEqualTo(4);
        assertThat(list).contains(experienceRound);
        assertThat(list).contains(experienceRound1);
        assertThat(list).contains(experienceRound2);
        assertThat(list).contains(experienceRound3);
        assertThat(list.get(2)).isSameAs(experienceRound2);
        assertThat(list.get(1).getEndTime()).isEqualTo(LocalTime.of(12, 0));
    }

    @Test
    @DisplayName("findAll")
    void findAll() {
        //given
        ExperienceRound experienceRound1 = makeTestExperienceRound(experience);
        ExperienceRound experienceRound2 = makeTestExperienceRound(experience);
        ExperienceRound experienceRound3 = makeTestExperienceRound(experience);

        experienceRoundRepository.save(experienceRound);
        experienceRoundRepository.save(experienceRound1);
        experienceRoundRepository.save(experienceRound2);
        experienceRoundRepository.save(experienceRound3);
        //when
        List<ExperienceRound> all = experienceRoundRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(4);
        assertThat(all).contains(experienceRound);
        assertThat(all).contains(experienceRound1);
        assertThat(all).contains(experienceRound2);
        assertThat(all).contains(experienceRound3);
    }
}