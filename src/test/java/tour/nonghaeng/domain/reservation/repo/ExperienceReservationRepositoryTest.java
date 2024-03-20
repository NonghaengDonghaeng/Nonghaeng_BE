package tour.nonghaeng.domain.reservation.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.repo.ExperienceCloseDateRepository;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.domain.experience.repo.ExperienceRoundRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.experience.TestExperienceCloseDate.makeTestExperienceCloseDate;
import static tour.nonghaeng.global.experience.TestExperienceRound.makeTestExperienceRound;
import static tour.nonghaeng.global.reservation.TestExperienceReservation.makeTestExperienceReservation;
import static tour.nonghaeng.global.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.tour.TestTour.makeTestTour;
import static tour.nonghaeng.global.user.TestUser.makeTestUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExperienceReservationRepositoryTest {

    @Autowired
    private ExperienceReservationRepository experienceReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceRoundRepository experienceRoundRepository;

    @Autowired
    private ExperienceCloseDateRepository experienceCloseDateRepository;

    private static User user;
    private static Seller seller;
    private static Tour tour;
    private static Experience experience;
    private static ExperienceRound experienceRound;
    private static ExperienceCloseDate experienceCloseDate;
    private static LocalDate reservationDate;
    @BeforeEach
    void setUp() {
        user = makeTestUser();
        userRepository.save(user);

        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);

        experience = makeTestExperience(tour);
        experienceRepository.save(experience);

        experienceRound = makeTestExperienceRound(experience);
        experienceRoundRepository.save(experienceRound);

        LocalDate closeDate = LocalDate.of(2024, 5, 5);
        experienceCloseDate = makeTestExperienceCloseDate(experience, closeDate);
        experienceCloseDateRepository.save(experienceCloseDate);

        reservationDate = LocalDate.of(2024, 3, 3);
    }

    @Test
    @DisplayName("저장및조회")
    void 저장및조회() {
        //given
        ExperienceReservation experienceReservation = makeTestExperienceReservation(user, experienceRound, reservationDate);
        ExperienceReservation saved = experienceReservationRepository.save(experienceReservation);
        //when
        Optional<ExperienceReservation> byId = experienceReservationRepository.findById(saved.getId());
        //then
        byId.ifPresent(experienceReservation1 -> {
            assertThat(experienceReservation1.getExperienceRound()).isSameAs(experienceRound);
            assertThat(experienceReservation1.getExperience()).isSameAs(experience);
            assertThat(experienceReservation1.getUser()).isSameAs(user);
            assertThat(experienceReservation1.getSeller()).isSameAs(seller);
            assertThat(experienceReservation1.getReservationDate()).isEqualTo(reservationDate);
        });

    }

    @Test
    void countParticipantByExperienceRoundAndReservationDate() {
        //given
        ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, 1);
        ExperienceReservation experienceReservation2 = makeTestExperienceReservation(user, experienceRound, LocalDate.of(2024,3,4), 2);
        ExperienceReservation experienceReservation3 = makeTestExperienceReservation(user, experienceRound, reservationDate, 3);

        experienceReservationRepository.save(experienceReservation1);
        experienceReservationRepository.save(experienceReservation2);
        experienceReservationRepository.save(experienceReservation3);
        //when
        Optional<Integer> num = experienceReservationRepository.countParticipantByExperienceRoundAndReservationDate(experienceRound, reservationDate);
        //then
        num.ifPresent(integer -> assertThat(integer).isEqualTo(4));

    }

    @Test
    void findAllBySeller() {
        //given
        ExperienceRound experienceRound1 = createExperienceRound();

        ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, 1);
        ExperienceReservation experienceReservation2 = makeTestExperienceReservation(user, experienceRound, LocalDate.of(2024,3,4), 2);
        ExperienceReservation experienceReservation3 = makeTestExperienceReservation(user, experienceRound1, reservationDate,3);

        experienceReservationRepository.save(experienceReservation1);
        experienceReservationRepository.save(experienceReservation2);
        experienceReservationRepository.save(experienceReservation3);
        //when
        PageRequest pageable = PageRequest.of(0, 10);
        Page<ExperienceReservation> allBySeller = experienceReservationRepository.findAllBySeller(seller, pageable);
        //then
        assertThat(allBySeller.getContent().size()).isEqualTo(2);
        assertThat(allBySeller.getContent()).contains(experienceReservation1);
        assertThat(allBySeller.getContent()).contains(experienceReservation2);

    }

    @Test
    void findAllByUser() {
        //given
        ExperienceRound experienceRound1 = createExperienceRound();

        ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, 1);
        ExperienceReservation experienceReservation2 = makeTestExperienceReservation(user, experienceRound, LocalDate.of(2024,3,4), 2);
        ExperienceReservation experienceReservation3 = makeTestExperienceReservation(user, experienceRound1, reservationDate,3);

        experienceReservationRepository.save(experienceReservation1);
        experienceReservationRepository.save(experienceReservation2);
        experienceReservationRepository.save(experienceReservation3);
        //when
        PageRequest pageable = PageRequest.of(0, 10);
        Page<ExperienceReservation> page = experienceReservationRepository.findAllByUser(user, pageable);
        //then
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getContent()).contains(experienceReservation1);
        assertThat(page.getContent()).contains(experienceReservation2);
        assertThat(page.getContent()).contains(experienceReservation3);
    }

    @Test
    void findSellerById() {
        //given
        ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, 1);
        experienceReservationRepository.save(experienceReservation1);
        //when
        Optional<Seller> sellerById = experienceReservationRepository.findSellerById(experienceReservation1.getId());
        //then
        sellerById.ifPresent(seller1 -> assertThat(seller1).isSameAs(seller));


    }

    @Test
    void findUserById() {
        //given
        ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, 1);
        ExperienceReservation saved = experienceReservationRepository.save(experienceReservation1);
        //when
        Optional<User> userById = experienceReservationRepository.findUserById(saved.getId());
        //then
        userById.ifPresent(user1 -> assertThat(user1).isSameAs(user));

    }

    @Test
    void existsById() {
        //given
        ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, 1);

        ExperienceReservation saved = experienceReservationRepository.save(experienceReservation1);
        //when
        boolean result1 = experienceReservationRepository.existsById(saved.getId());
        boolean result2 = experienceReservationRepository.existsById(1000L);
        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    private ExperienceRound createExperienceRound() {
        Seller seller1 = makeTestSeller();
        sellerRepository.save(seller1);
        Tour tour1 = makeTestTour(seller1);
        tourRepository.save(tour1);
        Experience experience1 = makeTestExperience(tour1);
        experienceRepository.save(experience1);
        ExperienceRound experienceRound1 = makeTestExperienceRound(experience1);
        return experienceRoundRepository.save(experienceRound1);
    }

}