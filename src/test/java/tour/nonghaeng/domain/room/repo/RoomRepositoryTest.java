package tour.nonghaeng.domain.room.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.room.TestRoom.makeTestRoom;
import static tour.nonghaeng.global.room.TestRoomCloseDate.*;
import static tour.nonghaeng.global.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.tour.TestTour.makeTestTour;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private RoomCloseDateRepository roomCloseDateRepository;

    private static Tour tour;
    private static Seller seller;
    private static Room room1;
    private static Room room2;
    private static RoomCloseDate roomCloseDate1;
    private static RoomCloseDate roomCloseDate2;


    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);

        room1 = makeTestRoom(tour, 0);
        room2 = makeTestRoom(tour, 1000);

    }

    @Test
    @DisplayName("숙소 저장 및 id로 조회")
    void 숙소저장및조회() {
        //given
        Room savedRoom = roomRepository.save(room1);
        //when
        Room findRoom = roomRepository.findById(savedRoom.getId()).get();
        //then
        assertThat(savedRoom).isSameAs(findRoom);
    }

    @Test
    @DisplayName("findMinPriceByTour: 여행지 숙소의 최소가격 찾기")
    void findMinPriceByTour() {
        //given
        roomRepository.save(room1);
        roomRepository.save(room2);
        //when
        Integer minPriceByTour = roomRepository.findMinPriceByTour(tour);
        //then
        assertThat(minPriceByTour).isEqualTo(10000);

    }

    @Test
    @DisplayName("findMaxPriceByTour: 여행지 숙소의 최고가격 찾기")
    void findMaxPriceByTour() {
        //given
        roomRepository.save(room1);
        roomRepository.save(room2);
        //when
        Integer maxPriceByTour = roomRepository.findMaxPriceByTour(tour);
        //then
        assertThat(maxPriceByTour).isEqualTo(21000);
    }

    @Test
    @DisplayName("findSellerByRoomId: roomId로 판매자 찾기")
    void findSellerByRoomId() {
        //given
        Room savedRoom = roomRepository.save(room1);

        //when
        Optional<Seller> sellerByRoomId = roomRepository.findSellerByRoomId(savedRoom.getId());
        //then
        sellerByRoomId.ifPresent(seller1 -> {
            assertThat(seller1).isSameAs(seller);
        });
    }

    @Test
    void findOldestCloseDate() {
        //given
        Room savedRoom = roomRepository.save(room1);

        roomCloseDate1 = makeTestRoomCloseDate(room1);
        roomCloseDate2 = makeTestRoomCloseDate(room1);
        roomCloseDateRepository.save(roomCloseDate1);
        roomCloseDateRepository.save(roomCloseDate2);
        //when
        Optional<LocalDate> oldestCloseDate = roomRepository.findOldestCloseDate(savedRoom.getId());
        //then
        oldestCloseDate.ifPresent(closeDate ->
                assertThat(closeDate).isEqualTo(LocalDate.of(ROOM_CLOSE_DATE_YEAR, ROOM_CLOSE_DATE_MONTH, ROOM_CLOSE_DATE_DAY)));
    }

    @Test
    @DisplayName("findAllIds: 모든 roomId 리스트 뽑기")
    void findAllIds() {
        //given
        roomRepository.save(room1);
        roomRepository.save(room2);
        //when
        List<Long> allIds = roomRepository.findAllIds();
        //then
        assertThat(allIds.size()).isEqualTo(2);
        assertThat(allIds).contains(room1.getId());
        assertThat(allIds).contains(room2.getId());
    }

    @Test
    @DisplayName("existsById: roomId가 존재할때 true")
    void existsByIdCase1() {
        //given
        Room savedRoom = roomRepository.save(room1);
        //when
        boolean result = roomRepository.existsById(savedRoom.getId());
        //then
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("existsById: roomId가 존재하지 않을때 false")
    void existsByIdCase2() {
        //given
        Room savedRoom = roomRepository.save(room1);
        //when
        boolean result = roomRepository.existsById(1000L);
        //then
        assertThat(result).isFalse();
    }
}