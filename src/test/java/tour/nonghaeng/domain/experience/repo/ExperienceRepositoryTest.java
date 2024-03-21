package tour.nonghaeng.domain.experience.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.testEntity.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.testEntity.experience.TestExperienceCloseDate.makeTestExperienceCloseDate;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExperienceRepositoryTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceRoundRepository experienceRoundRepository;

    @Autowired
    private ExperienceCloseDateRepository experienceCloseDateRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private SellerRepository sellerRepository;

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

    }

    @Test
    @DisplayName("체험 저장 및 조회")
    void 체험저장및조회() {
        //given
        Experience savedExperience = experienceRepository.save(experience);
        //when
        Optional<Experience> maybeExperience = experienceRepository.findById(savedExperience.getId());
        //then
        maybeExperience.ifPresent(findExperience -> {
            assertThat(findExperience).isSameAs(savedExperience);
            assertThat(findExperience.getTour()).isSameAs(tour);
        });

    }

    @Test
    @DisplayName("findSellerByExperienceId: experienceId 로 판매자 찾기")
    void findSellerByExperienceId() {
        //given
        Experience savedExperience = experienceRepository.save(experience);
        //when
        Optional<Seller> sellerByExperienceId = experienceRepository.findSellerByExperienceId(savedExperience.getId());
        //then
        sellerByExperienceId.ifPresent(seller1 -> assertThat(seller1).isSameAs(seller));

    }

    @Test
    @DisplayName("existsById: 존재할 때")
    void existsById() {
        //given
        Experience savedExperience = experienceRepository.save(experience);
        //when
        boolean result = experienceRepository.existsById(savedExperience.getId());
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("findOldestCloseDate: 오래된 날짜 찾기")
    void findOldestCloseDate() {
        //given
        Experience savedExperience = experienceRepository.save(experience);

        ExperienceCloseDate experienceCloseDate1 = makeTestExperienceCloseDate(experience,LocalDate.of(2024,5,5));
        ExperienceCloseDate experienceCloseDate2 = makeTestExperienceCloseDate(experience,LocalDate.of(2024,5,6));
        experienceCloseDateRepository.save(experienceCloseDate1);
        experienceCloseDateRepository.save(experienceCloseDate2);

        //when
        Optional<LocalDate> oldestCloseDate = experienceRepository.findOldestCloseDate(savedExperience.getId());
        //then
        oldestCloseDate.ifPresent(oldestCloseDate1 -> {
            assertThat(oldestCloseDate1).isEqualTo(experienceCloseDate1.getCloseDate());
            assertThat(oldestCloseDate1).isEqualTo(LocalDate.of(2024,5,5));
        });
    }

    @Test
    @DisplayName("findAllIds: 체험에 대한 모든 id 리스트로 받기")
    void findAllIds() {
        //given
        Experience experience2 = makeTestExperience(tour);
        Experience experience3 = makeTestExperience(tour);
        experienceRepository.save(experience);
        experienceRepository.save(experience2);
        experienceRepository.save(experience3);
        //when
        List<Long> allIds = experienceRepository.findAllIds();
        //then
        assertThat(allIds).contains(experience.getId());
        assertThat(allIds).contains(experience2.getId());
        assertThat(allIds).contains(experience3.getId());

        assertThat(allIds.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("findAll: 체험 페이지로 가져오기")
    @Order(1)
    void findAll() {
        //given
        PageRequest pageable = PageRequest.of(0, 10);
        Experience experience2 = makeTestExperience(tour);
        Experience experience3 = makeTestExperience(tour);
        Experience saved1 = experienceRepository.save(experience);
        Experience saved2 = experienceRepository.save(experience2);
        Experience saved3 = experienceRepository.save(experience3);
        //when
        Page<Experience> all = experienceRepository.findAll(pageable);
        //then
        assertThat(all).isInstanceOf(Page.class);
        assertThat(all.getContent().size()).isEqualTo(3);
        assertThat(all.getContent()).contains(saved1);
        assertThat(all.getContent()).contains(saved3);
        assertThat(all.getContent()).contains(saved2);
    }
}