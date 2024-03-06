package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.bank.BankCode;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.etc.role.Role;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)   //변수마다 @JsonProperty 사용 필요없이 모두 변환
public record SellerJoinDto(
        String phoneNumber,
        String businessNumber,
        String username,
        String name,
        String email,
        String password,
        String checkPassword,
        String address,
        String callNumber,
        AreaCode areaCode,
        BankCode bankCode,
        String bankAccount,
        String bankAccountName
) {
    public Seller toEntity(){
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
}
