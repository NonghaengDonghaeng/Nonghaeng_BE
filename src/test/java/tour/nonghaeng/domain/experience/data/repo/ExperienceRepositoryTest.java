package tour.nonghaeng.domain.experience.data.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;
import tour.nonghaeng.domain.experience.dto.ExpSpecDto;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.repo.SellerRepository;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.domain.tour.data.repo.TourRepository;
import tour.nonghaeng.global.infra.enums.experience.ExperienceType;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExperienceRepositoryTest {

    @Autowired
    private ExperienceRepository experienceRepository;

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
    @DisplayName("findSellerByExperienceId")
    void findSellerByExperienceId() {
        //given
        Experience savedExperience = experienceRepository.save(experience);

        //when
        Optional<Seller> sellerOptional = experienceRepository.findSellerByExperienceId(savedExperience.getId());
        //then
        sellerOptional.ifPresent(seller1 -> assertThat(seller1).isEqualTo(seller));

    }

    @Test
    @DisplayName("existsById")
    void existsById() {
        //given
        Experience savedExperience = experienceRepository.save(experience);
        //when
        boolean result1 = experienceRepository.existsById(savedExperience.getId());
        boolean result2 = experienceRepository.existsById(40L);
        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void test() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("findOldestCloseDate")
    void findOldestCloseDate() {
        //given
        Experience savedExperience = experienceRepository.save(experience);


        ExperienceCloseDate experienceCloseDate1 = makeTestExperienceCloseDate(experience, LocalDate.of(2025, 1, 1));
        ExperienceCloseDate experienceCloseDate2 = makeTestExperienceCloseDate(experience, LocalDate.of(2025, 1, 2));

        experienceCloseDateRepository.save(experienceCloseDate1);
        experienceCloseDateRepository.save(experienceCloseDate2);
        //when
        Optional<LocalDate> oldestCloseDate = experienceRepository.findOldestCloseDate(savedExperience.getId());
        //then
        oldestCloseDate.ifPresent(closeDate -> {
            assertThat(closeDate).isEqualTo(experienceCloseDate1.getCloseDate());
            assertThat(closeDate).isEqualTo(LocalDate.of(2025, 1, 1));
        });

    }

    @Test
    @DisplayName("findAllIds")
    void findAllIds() {
        //given
        Experience experience1 = makeTestExperience(tour);
        Experience experience2 = makeTestExperience(tour);
        experienceRepository.save(experience);
        experienceRepository.save(experience1);
        experienceRepository.save(experience2);
        //when
        List<Long> ids = experienceRepository.findAllIds();
        //then
        assertThat(ids.size()).isEqualTo(3);

        assertThat(ids).contains(experience.getId());
        assertThat(ids).contains(experience1.getId());
        assertThat(ids).contains(experience2.getId());
    }

    @Nested
    @DisplayName("findAll() 테스트")
    class findAllTest {
        @Test
        @DisplayName("모두찾기")
        void findAll1() {
            //given
            PageRequest pageable = PageRequest.of(0, 2);
            Experience experience1 = makeTestExperience(tour);
            Experience experience2 = makeTestExperience(tour);

            Experience saved1 = experienceRepository.save(experience);
            Experience saved2 = experienceRepository.save(experience1);
            Experience saved3 = experienceRepository.save(experience2);
            //when
            Page<Experience> all = experienceRepository.findAll(pageable);
            //then
            assertThat(all).isInstanceOf(Page.class);
            assertThat(all.getTotalElements()).isEqualTo(3);
            assertThat(all.getTotalPages()).isEqualTo(2);
            assertThat(all.getContent()).hasSize(2);
            assertThat(all.getContent()).contains(saved1);
            assertThat(all.getContent()).contains(saved2);
        }

        @Test
        @DisplayName("이름으로 검색 필터")
        void findAll2() {
            //given
            PageRequest pageable = PageRequest.of(0, 4);
            ExpSpecDto specDto = new ExpSpecDto();
            specDto.setKeyword("test");
            Specification<Experience> spec = specDto.buildSpecification();
            Experience experience1 = makeTestExperience(tour,"test");
            Experience experience2 = makeTestExperience(tour);

            experienceRepository.save(experience);
            Experience saved = experienceRepository.save(experience1);
            experienceRepository.save(experience2);
            //when
            Page<Experience> all = experienceRepository.findAll(spec,pageable);
            //then
            assertThat(all).isInstanceOf(Page.class);
            assertThat(all.getTotalElements()).isEqualTo(1);

            assertThat(all.getContent()).contains(saved);
        }
        @Test
        @DisplayName("타입으로 검색")
        void findAll3() {
            //given
            PageRequest pageable = PageRequest.of(0, 4);
            ExpSpecDto specDto = new ExpSpecDto();
            specDto.setExperienceType(ExperienceType.RURAL);
            Specification<Experience> spec = specDto.buildSpecification();
            Experience experience1 = makeTestExperience(tour, ExperienceType.RURAL);
            Experience experience2 = makeTestExperience(tour);

            experienceRepository.save(experience);
            Experience saved = experienceRepository.save(experience1);
            experienceRepository.save(experience2);
            //when
            Page<Experience> all = experienceRepository.findAll(spec,pageable);
            //then
            assertThat(all).isInstanceOf(Page.class);
            assertThat(all.getTotalElements()).isEqualTo(1);
            assertThat(all.getContent()).contains(saved);
            assertThat(all.getContent().get(0)).isEqualTo(saved);
        }
    }


}


