package tour.nonghaeng.domain.member.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.bank.BankCode;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.Seller;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class SellerRepositoryTest {

    @Autowired
    private SellerRepository sellerRepository;

    private static Seller seller;

    public static final Role SELLER_ROLE = Role.SELLER;
    public static final AreaCode SELLER_AREA_CODE = AreaCode.BUSAN;
    public static final String SELLER_PHONE_NUMBER = "010-2345-2345";
    public static final String SELLER_BUSINESS_NUMBER = "123456789";
    public static final String SELLER_USER_NAME = "testSeller";
    public static final String SELLER_PASSWORD = "1234";
    public static final String SELLER_NAME = "testSeller";
    public static final String SELLER_EMAIL = "testSeller@email.com";
    public static final String SELLER_ADDRESS = "대전광역시 유성구 노은로 123";
    public static final String SELLER_CALL_NUMBER = "042-1234-1234";
    public static final BankCode SELLER_BANK_CODE = BankCode.KOREA;
    public static final String SELLER_BANK_ACCOUNT = "12-34567-89";
    public static final String SELLER_BANK_ACCOUNT_NAME = "tesSeller";

    @BeforeEach
    void setup() {
        seller = makeTestSeller();
    }

    @Test
    @DisplayName("회원 저장 후 조회")
    void 회원저장과조회() {
        //given
        Seller savedSeller = sellerRepository.save(seller);
        //when
        Seller findSeller = sellerRepository.findByUsername(savedSeller.getUsername()).get();
        //then
        assertThat(savedSeller).isSameAs(findSeller);
        assertThat(savedSeller.getId()).isEqualTo(findSeller.getId());
        assertThat(savedSeller.getBusinessNumber()).isEqualTo(findSeller.getBusinessNumber());
    }

}