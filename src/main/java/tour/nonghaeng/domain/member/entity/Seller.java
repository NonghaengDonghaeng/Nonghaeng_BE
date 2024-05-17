package tour.nonghaeng.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.bank.BankCode;
import tour.nonghaeng.domain.etc.enums.role.Role;

@Entity
@Table(name = "SELLERS")
@DiscriminatorValue("seller")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Seller extends Member{


    private String address;

    private String callNumber;

    private String businessNumber;

    @Enumerated(EnumType.STRING)
    private BankCode bankCode;
    private String bankAccount;
    private String bankAccountName;

    @Builder
    private Seller(Role role, String phoneNumber, String name, String email, String username, String password, String refreshToken, int point, AreaCode areaCode, boolean marketingConsent, String address, String callNumber, String businessNumber, BankCode bankCode, String bankAccount, String bankAccountName) {
        super(role, phoneNumber, name, email, username, password, refreshToken, point, areaCode, marketingConsent);
        this.address = address;
        this.callNumber = callNumber;
        this.businessNumber = businessNumber;
        this.bankCode = bankCode;
        this.bankAccount = bankAccount;
        this.bankAccountName = bankAccountName;
    }


    public int payBackPoint(int price) {
        super.point += price;

        return super.point;
    }
}
