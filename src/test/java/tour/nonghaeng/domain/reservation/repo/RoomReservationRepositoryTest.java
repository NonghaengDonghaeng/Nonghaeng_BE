package tour.nonghaeng.domain.reservation.repo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;
import tour.nonghaeng.domain.room.repo.RoomCloseDateRepository;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.reservation.TestRoomReservation.makeTestRoomReservation;
import static tour.nonghaeng.global.room.TestRoom.makeTestRoom;
import static tour.nonghaeng.global.room.TestRoomCloseDate.makeTestRoomCloseDate;
import static tour.nonghaeng.global.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.tour.TestTour.makeTestTour;
import static tour.nonghaeng.global.user.TestUser.makeTestUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoomReservationRepositoryTest {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomCloseDateRepository roomCloseDateRepository;

    private static User user;
    private static Seller seller;
    private static Tour tour;
    private static Room room;
    private static RoomCloseDate roomCloseDate;
    private static List<LocalDate> reservationDates = new ArrayList<>();
    private static LocalDate reservationDate1;
    private static LocalDate reservationDate2;
    private static LocalDate reservationDate3;
    @BeforeEach
    void setUp() {
        user = makeTestUser();
        userRepository.save(user);

        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);

        room = makeTestRoom(tour, 0);
        roomRepository.save(room);

        LocalDate closeDate = LocalDate.of(2024, 5, 5);
        roomCloseDate = makeTestRoomCloseDate(room, closeDate);
        roomCloseDateRepository.save(roomCloseDate);

        reservationDate1 = LocalDate.of(2024, 4, 1);
        reservationDate2 = LocalDate.of(2024, 4, 2);
        reservationDate3 = LocalDate.of(2024, 4, 3);
        reservationDates.add(reservationDate1);
        reservationDates.add(reservationDate2);
        reservationDates.add(reservationDate3);
    }

    @Test
    @DisplayName("저장 및 조회")
    void 저장및조회() {
        //given
        RoomReservation roomReservation = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation saved = roomReservationRepository.save(roomReservation);
        //when
        Optional<RoomReservation> byId = roomReservationRepository.findById(saved.getId());
        //then
        byId.ifPresent(roomReservation1 -> {
            assertThat(roomReservation1).isSameAs(saved);
            assertThat(roomReservation1.getReservationDates()
                    .stream().map(roomReservationDate ->
                            roomReservationDate.getReservationDate())
                    .toList())
                    .contains(reservationDate1);
            assertThat(roomReservation1.getReservationDates()
                    .stream().map(roomReservationDate ->
                            roomReservationDate.getReservationDate())
                    .toList())
                    .contains(reservationDate3);
        });

    }

    @Test
    void countByRoomAndReservationDate() {
        //given
        RoomReservation roomReservation1 = makeTestRoomReservation(user, room,
                List.of(LocalDate.of(2024, 7, 1),
                        LocalDate.of(2024,7,2),
                        LocalDate.of(2024,7,3)),
                1);
        User user2 = makeTestUser();
        userRepository.save(user2);
        RoomReservation roomReservation2 = makeTestRoomReservation(
                user2, room,
                List.of(LocalDate.of(2024, 7, 1),
                        LocalDate.of(2024,7,2)),
                2);
        roomReservationRepository.save(roomReservation1);
        roomReservationRepository.save(roomReservation2);
        //when
        Optional<Integer> num = roomReservationRepository.countByRoomAndReservationDate(room, LocalDate.of(2024, 7, 1));
        System.out.println("num = " + num.get());
        //then
        num.ifPresent(integer -> assertThat(integer).isEqualTo(3));
    }

    @Test
    void findAllByUser() {
        //given
        User user2 = makeTestUser();
        userRepository.save(user2);

        RoomReservation roomReservation1 = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation roomReservation2 = makeTestRoomReservation(user2, room, reservationDates);
        RoomReservation roomReservation3 = makeTestRoomReservation(user, room,
                List.of(LocalDate.of(2024, 4, 6),
                        LocalDate.of(2024, 4, 7)));
        roomReservationRepository.save(roomReservation1);
        roomReservationRepository.save(roomReservation2);
        roomReservationRepository.save(roomReservation3);
        //when
        PageRequest pageable = PageRequest.of(0, 10);
        Page<RoomReservation> page = roomReservationRepository.findAllByUser(user, pageable);
        //then
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent()).contains(roomReservation1);
        assertThat(page.getContent()).doesNotContain(roomReservation2);
    }

    @Test
    void findAllBySeller() {
        //given
        Seller seller2 = makeTestSeller();
        sellerRepository.save(seller2);
        Tour tour2 = makeTestTour(seller2);
        tourRepository.save(tour2);
        Room room2 = makeTestRoom(tour2);
        roomRepository.save(room2);
        RoomReservation roomReservation1 = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation roomReservation2 = makeTestRoomReservation(user, room2, reservationDates);
        RoomReservation roomReservation3 = makeTestRoomReservation(user, room, List.of(LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 2)));

        roomReservationRepository.save(roomReservation1);
        roomReservationRepository.save(roomReservation2);
        roomReservationRepository.save(roomReservation3);
        //when
        PageRequest pageable = PageRequest.of(0, 10);
        Page<RoomReservation> page = roomReservationRepository.findAllBySeller(seller, pageable);
        //then
        assertThat(page.getContent().size()).isEqualTo(2);
        assertThat(page.getContent()).contains(roomReservation1);
        assertThat(page.getContent()).doesNotContain(roomReservation2);
    }

    @Test
    void findUserById() {
        //given
        RoomReservation roomReservation = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation saved = roomReservationRepository.save(roomReservation);
        //when
        Optional<User> userById = roomReservationRepository.findUserById(saved.getId());
        //then
        userById.ifPresent(user1 -> assertThat(user1).isSameAs(user));
    }

    @Test
    void findSellerById() {
        //given
        RoomReservation roomReservation = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation saved = roomReservationRepository.save(roomReservation);
        //when
        Optional<Seller> sellerById = roomReservationRepository.findSellerById(saved.getId());
        //then
        sellerById.ifPresent(seller1 -> assertThat(seller1).isSameAs(seller));

    }

    @Test
    void findStartDateById() {
        //given
        RoomReservation roomReservation1 = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation roomReservation2 = makeTestRoomReservation(user, room,
                List.of(LocalDate.of(2024, 6, 7),
                        LocalDate.of(2024,6,8),
                        LocalDate.of(2024,6,6)));
        RoomReservation saved1 = roomReservationRepository.save(roomReservation1);
        RoomReservation saved2 = roomReservationRepository.save(roomReservation2);
        //when
        Optional<LocalDate> startDate1 = roomReservationRepository.findStartDateById(saved1.getId());
        Optional<LocalDate> startDate2 = roomReservationRepository.findStartDateById(saved2.getId());
        //then
        startDate1.ifPresent(startDate -> assertThat(startDate).isEqualTo(reservationDate1));
        startDate2.ifPresent(startDate -> assertThat(startDate).isEqualTo(LocalDate.of(2024, 6, 6)));
    }

    @Test
    void existsById() {
        //given
        RoomReservation roomReservation = makeTestRoomReservation(user, room, reservationDates);
        RoomReservation saved = roomReservationRepository.save(roomReservation);
        //when
        boolean result1 = roomReservationRepository.existsById(saved.getId());
        boolean result2 = roomReservationRepository.existsById(1000L);
        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

}