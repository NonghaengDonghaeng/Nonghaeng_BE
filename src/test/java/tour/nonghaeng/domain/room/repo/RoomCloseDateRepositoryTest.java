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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.room.TestRoom.makeTestRoom;
import static tour.nonghaeng.global.room.TestRoomCloseDate.*;
import static tour.nonghaeng.global.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.tour.TestTour.makeTestTour;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class RoomCloseDateRepositoryTest {

    @Autowired
    private RoomCloseDateRepository roomCloseDateRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TourRepository tourRepository;

    private static Seller seller;
    private static Tour tour;
    private static Room room;
    private static RoomCloseDate roomCloseDate1;
    private static RoomCloseDate roomCloseDate2;

    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        sellerRepository.save(seller);

        tour = makeTestTour(seller);
        tourRepository.save(tour);

        room = makeTestRoom(tour, 0);
        roomRepository.save(room);

        LocalDate closeDate1 = LocalDate.of(2024, 5, 5);
        LocalDate closeDate2 = LocalDate.of(2024, 5, 6);
        roomCloseDate1 = makeTestRoomCloseDate(room,closeDate1);
        roomCloseDate2 = makeTestRoomCloseDate(room,closeDate2);
    }

    @Test
    @DisplayName("저장 및 조회")
    void 저장및조회() {
        //given
        RoomCloseDate savedRoomCloseDate = roomCloseDateRepository.save(roomCloseDate1);
        //when
        Room getRoom = savedRoomCloseDate.getRoom();
        Seller getSeller = savedRoomCloseDate.getRoom().getSeller();
        Tour getTour = savedRoomCloseDate.getRoom().getTour();
        //then
        assertThat(getRoom).isSameAs(room);
        assertThat(getSeller).isSameAs(seller);
        assertThat(getTour).isSameAs(tour);
    }

    @Test
    @DisplayName("existsByRoomAndCloseDate: 방와 오픈날짜 존재하는지 체크, 존재할 때")
    void existsByRoomAndCloseDate_CASE1() {
        //given
        roomCloseDateRepository.save(roomCloseDate1);
        roomCloseDateRepository.save(roomCloseDate2);
        //when
        boolean result1 = roomCloseDateRepository.existsByRoomAndCloseDate(room,LocalDate.of(2024, 5, 5));
        boolean result2 = roomCloseDateRepository.existsByRoomAndCloseDate(room,LocalDate.of(2024, 5, 6));
        //then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

    @Test
    @DisplayName("existsByRoomAndCloseDate: 방와 오픈날짜 존재하는지 체크")
    void existsByRoomAndCloseDate_CASE2() {
        //given
        roomCloseDateRepository.save(roomCloseDate1);
        roomCloseDateRepository.save(roomCloseDate2);
        //when
        boolean result1 = roomCloseDateRepository.existsByRoomAndCloseDate(room,LocalDate.of(2024,6,6));
        boolean result2 = roomCloseDateRepository.existsByRoomAndCloseDate(room,LocalDate.of(2024,6,10));
        //then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("findByRoomAndCloseDate: 존재할 때")
    void findByRoomAndCloseDate() {
        //given
        RoomCloseDate savedRoomCloseDate1 = roomCloseDateRepository.save(roomCloseDate1);
        RoomCloseDate savedRoomCloseDate2 = roomCloseDateRepository.save(roomCloseDate2);

        LocalDate closeDate1 = LocalDate.of(2024,5,5);
        LocalDate closeDate2 = LocalDate.of(2024,5,6);

        //when
        Optional<RoomCloseDate> byRoomAndCloseDate1 = roomCloseDateRepository.findByRoomAndCloseDate(room, closeDate1);
        Optional<RoomCloseDate> byRoomAndCloseDate2 = roomCloseDateRepository.findByRoomAndCloseDate(room, closeDate2);
        //then
        byRoomAndCloseDate1.ifPresent(roomCloseDate ->
                assertThat(roomCloseDate).isSameAs(savedRoomCloseDate1));

        byRoomAndCloseDate2.ifPresent(roomCloseDate ->
                assertThat(roomCloseDate).isSameAs(savedRoomCloseDate2));

        byRoomAndCloseDate1.ifPresent(roomCloseDate -> assertThat(roomCloseDate.getCloseDate()).isEqualTo(closeDate1));
        byRoomAndCloseDate2.ifPresent(roomCloseDate -> assertThat(roomCloseDate.getRoom()).isSameAs(room));


    }
}