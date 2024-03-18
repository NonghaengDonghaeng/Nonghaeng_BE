package tour.nonghaeng.domain.tour.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.etc.tour.TourType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.tour.entity.Tour;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.seller.TestSeller.*;
import static tour.nonghaeng.global.tour.TestTour.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private static Tour tour;
    private static Seller seller;

    @BeforeEach
    void setup() {

        seller = Seller.builder()
                .role(SELLER_ROLE)
                .areaCode(SELLER_AREA_CODE)
                .username(SELLER_USER_NAME)
                .password(SELLER_PASSWORD)
                .name(SELLER_NAME)
                .businessNumber(SELLER_BUSINESS_NUMBER)
                .email(SELLER_EMAIL)
                .address(SELLER_ADDRESS)
                .phoneNumber(SELLER_PHONE_NUMBER)
                .callNumber(SELLER_CALL_NUMBER)
                .bankCode(SELLER_BANK_CODE)
                .bankAccount(SELLER_BANK_ACCOUNT)
                .bankAccountName(SELLER_BANK_ACCOUNT_NAME)
                .build();
        sellerRepository.save(seller);

        tour = Tour.builder()
                .seller(seller)
                .tourType(TourType.ETC)
                .name(TOUR_NAME)
                .homepageUrl(TOUR_HOMEPAGE_URL)
                .introduction(TOUR_INTRODUCTION)
                .oneLineIntro(TOUR_ONE_LINE_INTRO)
                .summary(TOUR_SUMMARY)
                .restaurant(TOUR_RESTAURANT)
                .parking(TOUR_PARKING)
                .toilet(TOUR_TOILET)
                .amenities(TOUR_AMENITIES)
                .build();
    }

    @Test
    @DisplayName("findBySeller 적용")
    void findBySeller() {
        //given
        Tour savedTour = tourRepository.save(tour);
        //when
        Tour findTour = tourRepository.findBySeller(seller).get();
        //then
        assertThat(savedTour.getId()).isEqualTo(findTour.getId());
    }

    @Test
    @DisplayName("existsBySeller: 존재할때")
    void existsBySellerCase1() {
        //given
        Tour savedTour = tourRepository.save(tour);
        //when
        boolean result = tourRepository.existsBySeller(seller);
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsBySeller: 존재하지 않을 떄")
    void existsBySellerCase2() {
        //given
        Seller seller2 = Seller.builder()
                .role(SELLER_ROLE)
                .areaCode(SELLER_AREA_CODE)
                .username(SELLER_USER_NAME+"1")
                .password(SELLER_PASSWORD+"1")
                .name(SELLER_NAME+"1")
                .businessNumber(SELLER_BUSINESS_NUMBER+"1")
                .email(SELLER_EMAIL+"1")
                .address(SELLER_ADDRESS+"1")
                .phoneNumber(SELLER_PHONE_NUMBER+"1")
                .callNumber(SELLER_CALL_NUMBER+"1")
                .bankCode(SELLER_BANK_CODE)
                .bankAccount(SELLER_BANK_ACCOUNT)
                .bankAccountName(SELLER_BANK_ACCOUNT_NAME)
                .build();
        sellerRepository.save(seller2);
        tourRepository.save(tour);
        //when
        boolean result = tourRepository.existsBySeller(seller2);
        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("existsById : 존재할때")
    void existsByIdCase1() {
        //given
        Long savedTourId = tourRepository.save(tour).getId();
        //when
        boolean result = tourRepository.existsById(savedTourId);
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsById : 존재하지 않을때")
    void existsByIdCase2() {
        //given
        Long savedTourId = tourRepository.save(tour).getId();
        Long notExistId = savedTourId + 100L;
        //when
        boolean result = tourRepository.existsById(notExistId);
        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("findAll")
    void findAll() {
        //given
        tourRepository.save(tour);
        int size = tourRepository.findAll().size();
        //when

        //then
        assertThat(size).isEqualTo(1);
    }
}