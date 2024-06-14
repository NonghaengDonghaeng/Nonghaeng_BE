package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.bank.BankCode;
import tour.nonghaeng.global.infra.enums.role.Role;

@JsonTypeName("seller")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)   //변수마다 @JsonProperty 사용 필요없이 모두 변환
@Getter
public class SellerJoinDto extends JoinDto {
    private final String businessNumber;
    private final String address;
    private final String callNumber;
    private final BankCode bankCode;
    private final String bankAccount;
    private final String bankAccountName;

    @Builder
    public SellerJoinDto(String phoneNumber, String businessNumber, String username, String name, String email, String password, String checkPassword, String address, String callNumber, AreaCode areaCode, BankCode bankCode, String bankAccount, String bankAccountName) {
        super(areaCode,phoneNumber,name,email,username,password,checkPassword);
        this.businessNumber = businessNumber;
        this.address = address;
        this.callNumber = callNumber;
        this.bankCode = bankCode;
        this.bankAccount = bankAccount;
        this.bankAccountName = bankAccountName;
    }

    public Seller toEntity() {
        return Seller.builder()
                .phoneNumber(this.getPhoneNumber())
                .businessNumber(this.businessNumber)
                .username(this.getUsername())
                .name(this.getName())
                .email(this.getEmail())
                .password(this.getPassword())
                .address(this.address)
                .callNumber(this.callNumber)
                .areaCode(this.getAreaCode())
                .bankCode(this.bankCode)
                .bankAccount(this.bankAccount)
                .bankAccountName(this.bankAccountName)
                .role(Role.SELLER)
                .build();
    }
}
