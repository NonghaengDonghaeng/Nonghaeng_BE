package tour.nonghaeng.global.seller;

import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.bank.BankCode;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.Seller;

public class TestSeller {

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

    public static int num = -1;


    public static Seller makeTestSeller() {
        num++;
        return Seller.builder()
                .role(SELLER_ROLE)
                .areaCode(SELLER_AREA_CODE)
                .username(SELLER_USER_NAME + String.valueOf(num))
                .password(SELLER_PASSWORD)
                .name(SELLER_NAME + String.valueOf(num))
                .businessNumber(SELLER_BUSINESS_NUMBER + String.valueOf(num))
                .email(SELLER_EMAIL + String.valueOf(num))
                .address(SELLER_ADDRESS + String.valueOf(num))
                .phoneNumber(SELLER_PHONE_NUMBER + String.valueOf(num))
                .callNumber(SELLER_CALL_NUMBER + String.valueOf(num))
                .bankCode(SELLER_BANK_CODE)
                .bankAccount(SELLER_BANK_ACCOUNT)
                .bankAccountName(SELLER_BANK_ACCOUNT_NAME)
                .build();
    }


}
