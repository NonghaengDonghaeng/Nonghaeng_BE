package tour.nonghaeng.domain.member.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.role.Role;
import tour.nonghaeng.global.infra.enums.social.SocialType;

@Entity
@Table(name = "USERS")
@DiscriminatorValue("user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends Member {


    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;


    @Builder
    private User(SocialType socialType, String socialId, Role role, String phoneNumber, String name, String email, String username, String password, String refreshToken, int point, AreaCode areaCode, boolean marketingConsent) {
        super(role, phoneNumber, name, email, username, password, refreshToken, point, areaCode,marketingConsent);
        this.socialType = socialType;
        this.socialId = socialId;
    }


    public void authorizeUser(){
        super.role = Role.USER;
    }

    public void givePoint(int point) {
        super.point += point;
    }

    public int payPoint(int price) {
        super.point -= price;
        return super.getPoint();
    }

    public int payBackPoint(int price, CancelPolicy cancelPolicy) {

        int payBackPoint = (int) (price * (1 - cancelPolicy.getPercent()));
        super.point += payBackPoint;

        return payBackPoint;
    }

    public int myRemainPoint() {
        return super.point;
    }
}
