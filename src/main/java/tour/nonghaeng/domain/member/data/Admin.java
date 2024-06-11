package tour.nonghaeng.domain.member.data;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.role.Role;

@Entity
@Table(name = "ADMINS")
@DiscriminatorValue("admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Admin extends Member {

    @Builder
    private Admin(Role role, String phoneNumber, String name, String email, String username, String password, String refreshToken, int point, AreaCode areaCode, boolean marketingConsent) {
        super(role, phoneNumber, name, email, username, password, refreshToken, point, areaCode,marketingConsent);
    }

    public int payPoint(int price) {
        super.point -= price;
        return super.getPoint();
    }

    public int payBackPoint(int price) {
        super.point += price;

        return super.point;
    }
}
