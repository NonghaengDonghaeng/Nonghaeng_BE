package tour.nonghaeng.domain.member.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.bank.BankCode;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.Seller;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    }

    @Test
    @DisplayName("회원 저장 후 조회")
    void 회원저장과조회() {
        //given
        Seller savedSeller = sellerRepository.save(seller);
        //when
        Seller findSeller = sellerRepository.findByUsername(SELLER_USER_NAME).get();
        //then
        assertThat(savedSeller).isSameAs(findSeller);
        assertThat(savedSeller.getId()).isEqualTo(findSeller.getId());
        assertThat(savedSeller.getBusinessNumber()).isEqualTo(findSeller.getBusinessNumber());
    }

}