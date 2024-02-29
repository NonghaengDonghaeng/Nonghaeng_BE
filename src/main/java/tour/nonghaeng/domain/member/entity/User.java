package tour.nonghaeng.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.member.enums.Role;
import tour.nonghaeng.domain.member.enums.SocialType;

@Entity
@Table(name="USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AreaCode areaCode;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;


    @Column(unique = true, length = 15)
    private String number;

    private String socialId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    private String refreshToken;

    private boolean marketingConsent;

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

    public void authorizeUser(){
        this.role = Role.USER;
    }

}
