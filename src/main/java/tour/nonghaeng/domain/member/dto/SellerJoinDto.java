package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.bank.BankCode;
import tour.nonghaeng.global.infra.enums.role.Role;
import tour.nonghaeng.domain.member.data.Seller;

@JsonTypeName("seller")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)   //변수마다 @JsonProperty 사용 필요없이 모두 변환
@NoArgsConstructor
@Getter
public class SellerJoinDto extends JoinDto {
    private String phoneNumber;
    private String businessNumber;
    private String username;
    private String name;
    private String email;
    private String password;
    private String checkPassword;
    private String address;
    private String callNumber;
    private AreaCode areaCode;
    private BankCode bankCode;
    private String bankAccount;
    private String bankAccountName;

    @Builder
    public SellerJoinDto(String phoneNumber, String businessNumber, String username, String name, String email, String password, String checkPassword, String address, String callNumber, AreaCode areaCode, BankCode bankCode, String bankAccount, String bankAccountName) {
        this.phoneNumber = phoneNumber;
        this.businessNumber = businessNumber;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.checkPassword = checkPassword;
        this.address = address;
        this.callNumber = callNumber;
        this.areaCode = areaCode;
        this.bankCode = bankCode;
        this.bankAccount = bankAccount;
        this.bankAccountName = bankAccountName;
    }

    public Seller toEntity() {
        return Seller.builder()
                .phoneNumber(this.phoneNumber)
                .businessNumber(this.businessNumber)
                .username(this.username)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .address(this.address)
                .callNumber(this.callNumber)
                .areaCode(this.areaCode)
                .bankCode(this.bankCode)
                .bankAccount(this.bankAccount)
                .bankAccountName(this.bankAccountName)
                .role(Role.SELLER)
                .build();
    }

    @Override
    public String toString() {
        return "SellerJoinDto{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", businessNumber='" + businessNumber + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                ", address='" + address + '\'' +
                ", callNumber='" + callNumber + '\'' +
                ", areaCode=" + areaCode +
                ", bankCode=" + bankCode +
                ", bankAccount='" + bankAccount + '\'' +
                ", bankAccountName='" + bankAccountName + '\'' +
                '}';
    }
}
