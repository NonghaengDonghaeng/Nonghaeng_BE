package tour.nonghaeng.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import tour.nonghaeng.domain.etc.bank.BankCode;
import tour.nonghaeng.domain.member.enums.Role;

@Entity
@Table(name = "SELLERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String email;

    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    private String callNumber;

    private String refreshToken;

    private boolean marketingConsent;

    @Enumerated(EnumType.STRING)
    private BankCode bankCode;
    private String bankAccount;
    private String bankAccountName;

    @Builder
    private Seller(Role role, String username, String password, String name, String businessNumber, String email, String address, String phoneNumber, String callNumber, String refreshToken, boolean marketingConsent, BankCode bankCode, String bankAccount, String bankAccountName) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
        this.businessNumber = businessNumber;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.callNumber = callNumber;
        this.refreshToken = refreshToken;
        this.marketingConsent = marketingConsent;
        this.bankCode = bankCode;
        this.bankAccount = bankAccount;
        this.bankAccountName = bankAccountName;
    }


    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
