package tour.nonghaeng.domain.member.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import tour.nonghaeng.domain.etc.BaseTimeEntity;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.role.Role;

@Entity
@Table(name = "MEMBERS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    protected Long id;

    @Enumerated(EnumType.STRING)
    protected Role role;

    @Column(unique = true)
    protected String phoneNumber;

    @Column(nullable = false)
    protected String name;

    @Column
    protected String email;

    @Column(unique = true)
    protected String username;

    protected String password;

    protected String refreshToken;

    protected int point;

    private boolean marketingConsent;

    @Enumerated(EnumType.STRING)
    private AreaCode areaCode;


    public Member(Role role, String phoneNumber, String name, String email, String username, String password, String refreshToken, int point, AreaCode areaCode, boolean marketingConsent) {
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
        this.point = point;
        this.areaCode = areaCode;
        this.marketingConsent = marketingConsent;
    }


    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }


}
