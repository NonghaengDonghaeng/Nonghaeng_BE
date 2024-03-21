package tour.nonghaeng.domain.tour.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.tour.entity.Tour;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.*;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private static Tour tour1;
    private static Tour tour2;
    private static Seller seller1;
    private static Seller seller2;

    @BeforeEach
    void setup() {

        seller1 = makeTestSeller();
        seller2 = makeTestSeller();

        sellerRepository.save(seller1);
        sellerRepository.save(seller2);

        tour1 = makeTestTour(seller1);
        tour2 = makeTestTour(seller2);

    }

    @Test
    @DisplayName("findBySeller 적용")
    void findBySeller() {
        //given
        Tour savedTour = tourRepository.save(tour1);
        //when
        Tour findTour = tourRepository.findBySeller(seller1).get();
        //then
        assertThat(savedTour.getId()).isEqualTo(findTour.getId());
    }

    @Test
    @DisplayName("existsBySeller: 존재할때")
    void existsBySellerCase1() {
        //given
        Tour savedTour = tourRepository.save(tour1);
        //when
        boolean result = tourRepository.existsBySeller(seller1);
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
        tourRepository.save(tour1);
        //when
        boolean result = tourRepository.existsBySeller(seller2);
        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("existsById : 존재할때")
    void existsByIdCase1() {
        //given
        Long savedTourId = tourRepository.save(tour1).getId();
        //when
        boolean result = tourRepository.existsById(savedTourId);
        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("existsById : 존재하지 않을때")
    void existsByIdCase2() {
        //given
        Long savedTourId = tourRepository.save(tour1).getId();
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

        PageRequest pageable = PageRequest.of(0, 10);

        tourRepository.save(tour1);
        tourRepository.save(tour2);
        Page<Tour> page = tourRepository.findAll(pageable);
        //when

        //then
        assertThat(page.getContent().size()).isEqualTo(2);
    }
}