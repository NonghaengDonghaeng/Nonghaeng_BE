package tour.nonghaeng.domain.room.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.etc.room.RoomType;
import tour.nonghaeng.domain.etc.tour.TourType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.repo.TourRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.room.TestRoom.*;
import static tour.nonghaeng.global.seller.TestSeller.*;
import static tour.nonghaeng.global.tour.TestTour.TOUR_NAME;

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

    private static Tour tour;
    private static Seller seller;
    private static Room room;
    private static Room room2;


    @BeforeEach
    void setUp() {
        seller = Seller.builder()
                .role(SELLER_ROLE)
                .areaCode(SELLER_AREA_CODE)
                .username(SELLER_USER_NAME)
                .password(SELLER_PASSWORD)
                .name(SELLER_NAME)
                .businessNumber(SELLER_BUSINESS_NUMBER)
                .email(SELLER_EMAIL)
                .phoneNumber(SELLER_PHONE_NUMBER)
                .build();
        sellerRepository.save(seller);

        tour = Tour.builder()
                .seller(seller)
                .tourType(TourType.CAMPING)
                .name(TOUR_NAME)
                .build();
        tourRepository.save(tour);

        room = Room.builder()
                .tour(tour)
                .seller(seller)
                .roomType(RoomType.VILLAGE)
                .roomName(ROOM_NAME)
                .summary(ROOM_SUMMARY)
                .pricePeak(ROOM_PRICE_PEAK)
                .priceOffPeak(ROOM_PRICE_OFF_PEAK)
                .priceHoliday(ROOM_PRICE_HOLIDAY)
                .standardCapacity(ROOM_STANDARD_CAPACITY)
                .maxCapacity(ROOM_MAX_CAPACITY)
                .additionalCost(ROOM_ADDITIONAL_COST)
                .checkinTime(ROOM_CHECKIN_TIME)
                .checkoutTime(ROOM_CHECKOUT_TIME)
                .numOfRoom(ROOM_NUM_OF_ROOM)
                .build();
        room2 = Room.builder()
                .tour(tour)
                .seller(seller)
                .roomType(RoomType.VILLAGE)
                .roomName(ROOM_NAME+"1")
                .summary(ROOM_SUMMARY)
                .pricePeak(ROOM_PRICE_PEAK+1000)
                .priceOffPeak(ROOM_PRICE_OFF_PEAK+1000)
                .priceHoliday(ROOM_PRICE_HOLIDAY+1000)
                .standardCapacity(ROOM_STANDARD_CAPACITY)
                .maxCapacity(ROOM_MAX_CAPACITY)
                .additionalCost(ROOM_ADDITIONAL_COST)
                .checkinTime(ROOM_CHECKIN_TIME)
                .checkoutTime(ROOM_CHECKOUT_TIME)
                .numOfRoom(ROOM_NUM_OF_ROOM)
                .build();
    }

    @Test
    @DisplayName("숙소 저장 및 조회")
    void 숙소저장및조회() {
        //given
        Room savedRoom = roomRepository.save(room);
        //when
        Room findRoom = roomRepository.findById(savedRoom.getId()).get();
        //then
        assertThat(savedRoom).isSameAs(findRoom);
    }

    @Test
    void findMinPriceByTour() {
    }

    @Test
    void findMaxPriceByTour() {
        //given
        roomRepository.save(room);
        roomRepository.save(room2);
        //when
        Integer maxPriceByTour = roomRepository.findMaxPriceByTour(tour);
        //then
        assertThat(maxPriceByTour).isEqualTo(21000);
    }

    @Test
    @DisplayName("숙소아이디로 판매자 찾기")
    void findSellerByRoomId() {
        //given
        Room savedRoom = roomRepository.save(room);

        //when
        Optional<Seller> sellerByRoomId = roomRepository.findSellerByRoomId(savedRoom.getId());
        //then
        sellerByRoomId.ifPresent(seller1 -> {
            assertThat(seller1).isSameAs(seller);
        });
    }

    @Test
    void findOldestCloseDate() {
    }

    @Test
    @DisplayName("findAllIds")
    void findAllIds() {
        //given
        roomRepository.save(room);
        roomRepository.save(room2);
        //when
        List<Long> allIds = roomRepository.findAllIds();
        //then
        assertThat(allIds.size()).isEqualTo(2);
        assertThat(allIds).contains(room.getId());
        assertThat(allIds).contains(room2.getId());
    }

    @Test
    @DisplayName("existsById: 존재할때")
    void existsByIdCase1() {
        //given
        Room savedRoom = roomRepository.save(room);
        //when
        boolean result = roomRepository.existsById(savedRoom.getId());
        //then
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("existsById: 존재하지 않을때")
    void existsByIdCase2() {
        //given
        Room savedRoom = roomRepository.save(room);
        //when
        boolean result = roomRepository.existsById(1000L);
        //then
        assertThat(result).isFalse();
    }
}